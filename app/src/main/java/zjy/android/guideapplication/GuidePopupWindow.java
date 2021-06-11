package zjy.android.guideapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.core.graphics.ColorUtils;

import java.util.List;

public class GuidePopupWindow extends PopupWindow {

    private final Context context;
    private final List<GuideGroup> groups;
    private int originalColor;

    public GuidePopupWindow(Context context, List<GuideGroup> groups) {
        this.context = context;
        this.groups = groups;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_guide, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTouchable(true);
        setAnimationStyle(0);

        originalColor = ((Activity) context).getWindow().getStatusBarColor();
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int barHeight = context.getResources().getDimensionPixelSize(resId);

        for (GuideGroup page : groups) {
            for (GuideItem item : page.getItems()) {
                item.init(context, barHeight);
            }
        }

        GuideView guideView = view.findViewById(R.id.guide_view);
        guideView.setPages(groups);
        guideView.setOnClickListener(v -> {
            if (!guideView.next()) {
                dismiss();
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        int shadeColor = context.getResources().getColor(R.color.alpha);
        ((Activity) context).getWindow().setStatusBarColor(ColorUtils.compositeColors(shadeColor, originalColor));
    }

    @Override
    public void dismiss() {
        ((Activity) context).getWindow().setStatusBarColor(originalColor);
        super.dismiss();
    }
}
