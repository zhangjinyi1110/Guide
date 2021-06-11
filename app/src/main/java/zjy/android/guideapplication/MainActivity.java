package zjy.android.guideapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.DataBindingUtil;

import zjy.android.guideapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        View.OnClickListener listener = v -> GuidePage.init(this)
                .add(new GuideItem.Builder().setTarget(binding.centerTop).setTipResId(R.layout.tip_arrows_top).setTipOffsetX(-100).build())
                .add(new GuideItem.Builder().setTarget(binding.centerBottom).setTipResId(R.layout.tip_arrows_bottom).setGravity(GravityEnum.TOP).build())
                .add(new GuideItem.Builder().setTarget(binding.centerLeft).setTipResId(R.layout.tip_arrows_left).setTipOffsetX(-150).setTipOffsetY(310).setGravity(GravityEnum.RIGHT).build())
                .add(new GuideItem.Builder().setTarget(binding.centerRight).setTipResId(R.layout.tip_arrows_right).setGravity(GravityEnum.LEFT).build())
                .start();
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