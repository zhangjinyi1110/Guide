package zjy.android.guideapplication;

import android.app.Activity;

import java.util.ArrayList;

/**
 * @Create zhang
 * @DATE 2021/6/9 0009
 * @DESC
 */
public class GuidePage {

    private final Activity activity;
    private final ArrayList<GuideGroup> groups;
    private GuideGroup currGroup;

    private GuidePage(Activity activity) {
        this.activity = activity;
        this.groups = new ArrayList<>();
    }

    public static GuidePage init(Activity activity) {
        return new GuidePage(activity);
    }

    public GuidePage add(GuideItem item) {
        currGroup = new GuideGroup(item);
        groups.add(currGroup);
        return this;
    }

    public GuidePage with(GuideItem item) {
        if (currGroup == null) {
            add(item);
        } else {
            currGroup.add(item);
        }
        return this;
    }

    public void start() {
        new GuidePopupWindow(activity, groups).showAsDropDown(activity.getWindow().getDecorView());
    }

}
