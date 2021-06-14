package zjy.android.guideapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public interface ICustomGuide {

    /**
     * 描绘高亮部分
     *
     * @param canvas 画布
     * @param path   路径（如有高亮部分路径，必须要添加到path）
     * @param paint  画笔
     */
    void onDrawHighlight(Canvas canvas, Path path, Paint paint);

    /**
     * 创建引导布局
     *
     * @param context 上下文
     * @param next    下一步回调
     * @param finish  完成回调
     * @return 引导布局
     */
    View onCreateGuideLayout(Context context, Callback next, Callback finish);

}
