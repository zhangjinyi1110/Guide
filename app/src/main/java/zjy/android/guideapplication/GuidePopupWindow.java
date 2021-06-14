package zjy.android.guideapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.core.graphics.ColorUtils;

import java.util.List;

public class GuidePopupWindow extends PopupWindow {

    private final Activity activity;
    private final List<GuideGroup> groups;
    private int originalColor;

    public GuidePopupWindow(Activity activity, List<GuideGroup> groups) {
        this.activity = activity;
        this.groups = groups;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_guide, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTouchable(true);
        setAnimationStyle(0);

        originalColor = activity.getWindow().getStatusBarColor();

        GuideView guideView = view.findViewById(R.id.guide_view);
        guideView.setGroups(groups);
        guideView.setOnFinishListener(this::dismiss);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        int shadeColor = activity.getResources().getColor(R.color.alpha);
        activity.getWindow().setStatusBarColor(ColorUtils.compositeColors(shadeColor, originalColor));
    }

    @Override
    public void dismiss() {
        activity.getWindow().setStatusBarColor(originalColor);
        super.dismiss();
    }
}
