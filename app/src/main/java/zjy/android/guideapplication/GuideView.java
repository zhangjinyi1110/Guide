package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    private final List<GuideGroup> groups = new ArrayList<>();

    private Path path;
    private Paint paint;
    private Callback nextCallback;
    private Callback finishCallback;

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
        paint = new Paint();
        nextCallback = this::next;
        finishCallback = this::finish;
    }

    public void setGroups(List<GuideGroup> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        update();
    }

    private void next() {
        if (groups.size() == 1) {
            finish();
        } else {
            groups.remove(0);
            update();
        }
    }

    private void finish() {
        if (onFinishListener != null) {
            onFinishListener.onFinish();
        }
    }

    private void update() {
        removeAllViews();
        for (ICustomGuide guide : groups.get(0).getGuides()) {
            View view = guide.onCreateGuideLayout(getContext(), nextCallback, finishCallback);
            if (view != null) {
                addView(view);
                setOnClickListener(null);
            } else {
                setOnClickListener(v -> next());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (groups.size() == 0) return;
        GuideGroup group = groups.get(0);
        path.reset();
        for (ICustomGuide guide : group.getGuides()) {
            guide.onDrawHighlight(canvas, path, paint);
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
        void onFinish();
    }

}
