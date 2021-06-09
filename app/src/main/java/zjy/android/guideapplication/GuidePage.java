package zjy.android.guideapplication;

import android.app.Activity;

import java.util.ArrayList;

/**
 * @Create zhang
 * @DATE 2021/6/9 0009
 * @DESC
 */
public class GuidePage {

    private Activity activity;
    private ArrayList<GuideGroup> groups;

    private GuidePage(Activity activity) {
        this.activity = activity;
    }

    public static GuidePage with(Activity activity) {
        return new GuidePage(activity);
    }

    public void start() {
        GuideActivity.start(activity, groups);
    }

}
