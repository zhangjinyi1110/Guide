package zjy.android.guideapplication;

/**
 * @Create zhang
 * @DATE 2021/6/15 0015
 * @DESC
 */
public class Padding {

    final float left;
    final float top;
    final float right;
    final float bottom;

    Padding(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public static Padding all(float padding) {
        return new Padding(padding, padding, padding, padding);
    }

    public static Padding only(float left, float top, float right, float bottom) {
        return new Padding(left, top, right, bottom);
    }

}
