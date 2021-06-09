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

    private final int resId;
    private final int offsetX;
    private final int offsetY;

    private final ShapeEnum shape;

    private final GravityEnum gravity;

    public GuideItem(View view) {
        this(view, ShapeEnum.RECTANGLE, 0, 0, 0, GravityEnum.BOTTOM);
    }

    public GuideItem(View view, ShapeEnum shape) {
        this(view, shape, 0, 0, 0, GravityEnum.BOTTOM);
    }

    public GuideItem(View view, int resId) {
        this(view, ShapeEnum.RECTANGLE, resId, 0, 0, GravityEnum.BOTTOM);
    }

    public GuideItem(View view, int resId, int offsetX, int offsetY) {
        this(view, ShapeEnum.RECTANGLE, resId, offsetX, offsetY, GravityEnum.BOTTOM);
    }

    public GuideItem(View view, ShapeEnum shape, int resId, int offsetX, int offsetY, GravityEnum gravity) {
        this.shape = shape;
        this.gravity = gravity;
        this.resId = resId;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        initPoint(view);
    }

    public void init(Context context, int barHeight) {
        top -= barHeight;
        bottom -= barHeight;
        initPath();
        initTipView(context);
    }

    private void initTipView(Context context) {
        if (resId == 0) return;
        tipView = LayoutInflater.from(context).inflate(resId, null);
        tipView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        switch (gravity) {
            case TOP:
                tipView.setY(top + offsetY - tipView.getHeight());
                break;
            case LEFT:
                tipView.setX(left + offsetX - tipView.getWidth());
                break;
            case RIGHT:
                tipView.setX(right + offsetX);
                break;
            case BOTTOM:
                tipView.setY(bottom + offsetY);
                break;
            default:
                break;
        }
    }

    private void initPath() {
        path = new Path();
        switch (shape) {
            case RECTANGLE:
                shapeRectangle();
                break;
            case CIRCLE:
                break;
            case OVAL:
                break;
            default:
                break;
        }
    }

    private void shapeRectangle() {
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.lineTo(right, bottom);
        path.lineTo(right, top);
        path.close();
    }

    private void initPoint(View view) {
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
}
