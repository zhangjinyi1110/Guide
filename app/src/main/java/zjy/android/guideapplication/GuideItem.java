package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

public class GuideItem implements Serializable {

    private int left;
    private int right;
    private int top;
    private int bottom;
    private Path path;
    private View tipView;
    private int nextClickId;
    private int skipClickId;
    private boolean emptyView = true;

    private int resId;
    private int offsetX;
    private int offsetY;

    private ShapeEnum shape;

    private GravityEnum gravity;

    public void init(Context context, int barHeight) {
        if (!emptyView) {
            top -= barHeight;
            bottom -= barHeight;
            initPath();
        }
        initTipView(context);
    }

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
        float centerX = left + (right - left) / 2f;
        float centerY = top + (bottom - top) / 2f;
        tipView.post(() -> {
            switch (gravity) {
                case TOP:
                    tipView.setY(top + offsetY - tipView.getHeight());
                    tipView.setX(offsetX + centerX - tipView.getWidth() / 2f);
                    break;
                case LEFT:
                    tipView.setX(left + offsetX - tipView.getWidth());
                    tipView.setY(offsetY + centerY - tipView.getHeight() / 2f);
                    break;
                case RIGHT:
                    tipView.setX(right + offsetX);
                    tipView.setY(offsetY + centerY - tipView.getHeight() / 2f);
                    break;
                case BOTTOM:
                    tipView.setY(bottom + offsetY);
                    tipView.setX(offsetX + centerX - tipView.getWidth() / 2f);
                    break;
                default:
                    break;
            }
            tipView.setVisibility(View.VISIBLE);
        });
    }

    private void initPath() {
        path = new Path();
        switch (shape) {
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

    private void shapeCircle() {
        float w = right - left;
        float h = bottom - top;
        float x = left + w / 2f;
        float y = top + h / 2f;
        path.addCircle(x, y, (float) (Math.sqrt(w * w + h * h) / 2), Path.Direction.CW);
    }

    private void shapeRectangle() {
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.lineTo(right, bottom);
        path.lineTo(right, top);
        path.close();
    }

    private void initPoint(View view) {
        emptyView = false;
        int[] lt = new int[2];
        view.getLocationOnScreen(lt);
        left = lt[0];
        right = left + view.getWidth();
        top = lt[1];
        bottom = top + view.getHeight();
    }

    public Path getPath() {
        return path;
    }

    public View getTipView() {
        return tipView;
    }

    public View getNextView() {
        return tipView.findViewById(nextClickId);
    }

    public View getSkipView() {
        return tipView.findViewById(skipClickId);
    }

    public static class Builder {

        private final GuideItem item;
        private View target;

        public Builder() {
            item = new GuideItem();
        }

        public Builder setTarget(View target) {
            this.target = target;
            return this;
        }

        public Builder setShape(ShapeEnum shape) {
            item.shape = shape;
            return this;
        }

        public Builder setGravity(GravityEnum gravity) {
            item.gravity = gravity;
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
            if (item.gravity == null) {
                item.gravity = GravityEnum.BOTTOM;
            }
            if (item.shape == null) {
                item.shape = ShapeEnum.RECTANGLE;
            }
            if (target != null) {
                item.initPoint(target);
            }
            return item;
        }

    }
}
