package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
                .add(new ICustomGuide() {
                    @Override
                    public void onDrawHighlight(Canvas canvas, Path path, Paint paint) {
                        Path path1 = new Path();
                        path.addRoundRect(100, 100, 300, 300, 10, 10, Path.Direction.CW);
                        path1.addRoundRect(50, 50, 350, 350, 10, 10, Path.Direction.CW);
                        path.op(path1, Path.Op.XOR);
                        paint.setColor(Color.argb(25, 0, 0, 0));
                        canvas.drawPath(path, paint);
                        path.reset();
                        path.addPath(path1);
                    }

                    @Override
                    public View onCreateGuideLayout(Context context, Callback next, Callback finish) {
                        return null;
                    }
                })
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