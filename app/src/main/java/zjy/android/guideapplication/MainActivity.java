package zjy.android.guideapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import zjy.android.guideapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        GuideItem item = new GuideItem.Builder()
                .setSkipClickId(R.id.skip)
                .setTipResId(R.layout.layout_skip)
                .build();
        View.OnClickListener listener = v -> GuidePage.init(this)
                .add(new GuideItem.Builder()
                        .setTarget(binding.centerTop)
                        .setTipResId(R.layout.tip_arrows_top)
                        .setTipOffsetX(-100)
                        .setNextClickId(R.id.tips)
                        .build())
                .with(item)
                .with(new GuideItem.Builder()
                        .setTarget(binding.centerBottom)
                        .setTipResId(R.layout.tip_arrows_bottom)
                        .setGravity(GravityEnum.TOP)
                        .setNextClickId(R.id.tips)
                        .setShape(ShapeEnum.CIRCLE)
                        .setTipOffsetY(-50)
                        .build())
                .add(new GuideItem.Builder()
                        .setTarget(binding.centerLeft)
                        .setTipResId(R.layout.tip_arrows_left)
                        .setTipOffsetX(-150)
                        .setTipOffsetY(310)
                        .setNextClickId(R.id.tips)
                        .setGravity(GravityEnum.RIGHT).build())
                .with(item)
                .add(new GuideItem.Builder()
                        .setTarget(binding.centerRight)
                        .setNextClickId(R.id.tips)
                        .setTipResId(R.layout.tip_arrows_right)
                        .setGravity(GravityEnum.LEFT)
                        .build())
                .with(item)
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