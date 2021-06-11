package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuideView extends FrameLayout {

    private final List<GuideGroup> pages = new ArrayList<>();

    private Path path;

    public GuideView(@NonNull Context context) {
        super(context);
        init();
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        path = new Path();
    }

    public void setPages(List<GuideGroup> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
        update();
    }

    public void next() {
        if (pages.size() == 1) {
            finish();
        } else {
            pages.remove(0);
            update();
        }
    }

    private void finish() {
        if (onFinishListener != null) {
            onFinishListener.inFinish();
        }
    }

    private void update() {
        removeAllViews();
        for (GuideItem item : pages.get(0).getItems()) {
            initTipView(item);
        }
    }

    private void initTipView(GuideItem item) {
        View tipView = item.getTipView();
        if (tipView != null) {
            addView(tipView);
            View next = item.getNextView();
            View skip = item.getSkipView();
            if (next == null && skip == null) {
                setOnClickListener(v -> next());
            }
            if (next != null) {
                next.setOnClickListener(v -> next());
            }
            if (skip != null) {
                skip.setOnClickListener(v -> finish());
            }
        } else {
            setOnClickListener(v -> next());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pages.size() == 0) return;
        GuideGroup page = pages.get(0);
        path.reset();
        for (GuideItem item : page.getItems()) {
            if (item.getPath() != null)
                path.addPath(item.getPath());
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
        } else {
            canvas.clipOutPath(path);
        }
        canvas.drawColor(getResources().getColor(R.color.alpha));
    }

    private OnFinishListener onFinishListener;

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public interface OnFinishListener {
        void inFinish();
    }

}
