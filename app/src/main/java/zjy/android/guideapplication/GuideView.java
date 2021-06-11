package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
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

    public boolean next() {
        if (pages.size() == 1) return false;
        pages.remove(0);
        update();
        return true;
    }

    private void update() {
        removeAllViews();
        for (GuideItem item : pages.get(0).getItems()) {
            if (item.getTipView() != null)
                addView(item.getTipView());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pages.size() == 0) return;
        GuideGroup page = pages.get(0);
        path.reset();
        for (GuideItem item : page.getItems()) {
            path.addPath(item.getPath());
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
        } else {
            canvas.clipOutPath(path);
        }
        canvas.drawColor(getResources().getColor(R.color.alpha));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
