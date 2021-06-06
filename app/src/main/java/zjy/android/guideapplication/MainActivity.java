package zjy.android.guideapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import zjy.android.guideapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        View.OnClickListener listener = v -> {
//            overridePendingTransition(0, 0);
            ArrayList<GuidePage> list = new ArrayList<>();
            list.add(new GuidePage(new GuideItem(binding.center, R.layout.tip_arrows_top)).add(new GuideItem(binding.centerBottom)));
            list.add(new GuidePage(new GuideItem(binding.centerLeft, R.layout.tip_arrows_top)));
            list.add(new GuidePage(new GuideItem(binding.rightTop, R.layout.tip_arrows_top)));
            GuideActivity.start(this, list);
        };
        binding.center.setOnClickListener(listener);
        binding.leftBottom.setOnClickListener(listener);
        binding.leftTop.setOnClickListener(listener);
        binding.centerLeft.setOnClickListener(listener);
        binding.rightBottom.setOnClickListener(listener);
        binding.rightTop.setOnClickListener(listener);
        binding.centerRight.setOnClickListener(listener);
        binding.centerTop.setOnClickListener(listener);
        binding.centerBottom.setOnClickListener(listener);
    }
}