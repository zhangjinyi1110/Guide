package zjy.android.guideapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends FragmentActivity {

    public static void start(Context context, ArrayList<GuidePage> pages) {
        context.startActivity(new Intent(context, GuideActivity.class)
                .putExtra("data", pages));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getWindow().setStatusBarColor(getResources().getColor(R.color.alpha));
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int barHeight = getResources().getDimensionPixelSize(resId);

        List<GuidePage> pages = (List<GuidePage>) getIntent().getSerializableExtra("data");
        for (GuidePage page : pages) {
            for (GuideItem item : page.getItems()) {
                item.init(this, barHeight);
            }
        }

        GuideView guideView = findViewById(R.id.guide_view);
        guideView.setPages(pages);
        guideView.setOnClickListener(v -> {
            if (!guideView.next()){
                finish();
            }
        });
    }

    @Override
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }
}
