package com.yanlei.fallingLeaves;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Yanlei on 2016/6/1.
 */
public class BarrierView extends View {
    //对外以公开树枝的宽度和高度
    public static double shuzhiH;
    public static double shuzhiW;
    //创建画笔
    Paint paint = new Paint();

    public BarrierView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // paint.setColor(Color.YELLOW);
        //canvas.drawRect(10, 10, 300,100, paint);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.shuzhi);
        Matrix matrix = new Matrix();

        matrix.postScale(0.8f, 0.8f);

        // 得到新的图片
        Bitmap newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix,
                true);
        shuzhiH = newbmp.getHeight();
        shuzhiW = newbmp.getWidth();
        canvas.drawBitmap(newbmp, 0, 80, paint);
        bmp.recycle();
        newbmp.recycle();
        System.gc();
    }

}