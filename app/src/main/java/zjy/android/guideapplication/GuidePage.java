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

    public GuidePage add(ICustomGuide guide) {
        currGroup = new GuideGroup(guide);
        groups.add(currGroup);
        return this;
    }

    public GuidePage with(ICustomGuide guide) {
        if (currGroup == null) {
            add(guide);
        } else {
            currGroup.add(guide);
        }
        return this;
    }

    public void start() {
        new GuidePopupWindow(activity, groups).showAsDropDown(activity.getWindow().getDecorView());
    }

}
