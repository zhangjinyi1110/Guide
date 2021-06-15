package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GuideItem implements ICustomGuide {

    private RectF highlightRectF;
    private Path highlightPath;
    private Shape shape;
    private Padding padding;

    private int resId;
    private int offsetX;
    private int offsetY;
    private View tipView;
    private int nextClickId;
    private int skipClickId;
    private boolean emptyView = true;
    private Direction direction;

    private void initTipView(Context context) {
        if (resId == 0) return;
        if (emptyView) {
            tipView = LayoutInflater.from(context).inflate(resId, null);
            tipView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return;
        }
        tipView = LayoutInflater.from(context).inflate(resId, null);
        tipView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tipView.setVisibility(View.INVISIBLE);
        float centerX = highlightRectF.left + (highlightRectF.right - highlightRectF.left) / 2f;
        float centerY = highlightRectF.top + (highlightRectF.bottom - highlightRectF.top) / 2f;
        tipView.post(() -> {
            switch (direction) {
                case TOP:
                    tipView.setY(highlightRectF.top + offsetY - tipView.getHeight());
                    tipView.setX(offsetX + centerX - tipView.getWidth() / 2f);
                    break;
                case LEFT:
                    tipView.setX(highlightRectF.left + offsetX - tipView.getWidth());
                    tipView.setY(offsetY + centerY - tipView.getHeight() / 2f);
                    break;
                case RIGHT:
                    tipView.setX(highlightRectF.right + offsetX);
                    tipView.setY(offsetY + centerY - tipView.getHeight() / 2f);
                    break;
                case BOTTOM:
                    tipView.setY(highlightRectF.bottom + offsetY);
                    tipView.setX(offsetX + centerX - tipView.getWidth() / 2f);
                    break;
                default:
                    break;
            }
            tipView.setVisibility(View.VISIBLE);
        });
    }

    private void initPath() {
        highlightPath = new Path();
        switch (shape) {
            case ROUND_RECTANGLE:
                shapeRoundRectangle();
                break;
            case RECTANGLE:
                shapeRectangle();
                break;
            case CIRCLE:
                shapeCircle();
                break;
            case OVAL:
                break;
            default:
                break;
        }
    }

    private void shapeRoundRectangle() {
        highlightPath.addRoundRect(highlightRectF, 25, 25, Path.Direction.CW);
    }

    private void shapeCircle() {
        float w = highlightRectF.right - highlightRectF.left;
        float h = highlightRectF.bottom - highlightRectF.top;
        float x = highlightRectF.left + w / 2f;
        float y = highlightRectF.top + h / 2f;
        highlightPath.addCircle(x, y, (float) (Math.sqrt(w * w + h * h) / 2), Path.Direction.CW);
    }

    private void shapeRectangle() {
        highlightPath.addRect(highlightRectF, Path.Direction.CW);
    }

    private void initPoint(View target, boolean hasStateBar) {
        emptyView = false;
        int barHeight = 0;
        if (hasStateBar) {
            int resId = target.getResources().getIdentifier("status_bar_height", "dimen", "android");
            barHeight = target.getResources().getDimensionPixelSize(resId);
        }
        int[] lt = new int[2];
        target.getLocationOnScreen(lt);
        if (padding == null) {
            padding = Padding.all(0);
        }
        highlightRectF = new RectF();
        highlightRectF.left = lt[0] - padding.left;
        highlightRectF.right = highlightRectF.left + target.getWidth() + padding.right;
        highlightRectF.top = lt[1] - barHeight - padding.top;
        highlightRectF.bottom = highlightRectF.top + target.getHeight() + padding.bottom;
    }

    @Override
    public void onDrawHighlight(Canvas canvas, Path path, Paint paint) {
        if (emptyView) return;
        initPath();
        path.addPath(this.highlightPath);
    }

    @Override
    public View onCreateGuideLayout(Context context, Callback next, Callback finish) {
        initTipView(context);
        if (tipView == null) return null;
        if (nextClickId != 0) {
            tipView.findViewById(nextClickId).setOnClickListener(v -> next.callback());
        }
        if (skipClickId != 0) {
            tipView.findViewById(skipClickId).setOnClickListener(v -> finish.callback());
        }
        return tipView;
    }

    public static class Builder {

        private final GuideItem item;
        private View target;
        private boolean hasStateBar = true;

        public Builder() {
            item = new GuideItem();
        }

        public Builder setTarget(View target) {
            this.target = target;
            return this;
        }

        public Builder setHasStateBar() {
            this.hasStateBar = false;
            return this;
        }

        public Builder setShape(Shape shape) {
            item.shape = shape;
            return this;
        }

        public Builder setDirection(Direction direction) {
            item.direction = direction;
            return this;
        }

        public Builder setTipResId(int resId) {
            item.resId = resId;
            return this;
        }

        public Builder setNextClickId(int clickId) {
            item.nextClickId = clickId;
            return this;
        }

        public Builder setSkipClickId(int clickId) {
            item.skipClickId = clickId;
            return this;
        }

        public Builder setTipOffsetX(int offsetX) {
            item.offsetX = offsetX;
            return this;
        }

        public Builder setTipOffsetY(int offsetY) {
            item.offsetY = offsetY;
            return this;
        }

        public GuideItem build() {
            if (item.direction == null) {
                item.direction = Direction.BOTTOM;
            }
            if (item.shape == null) {
                item.shape = Shape.ROUND_RECTANGLE;
            }
            if (target != null) {
                item.initPoint(target, hasStateBar);
            }
            return item;
        }

    }
}
