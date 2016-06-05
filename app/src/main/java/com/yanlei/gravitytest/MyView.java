package com.yanlei.gravitytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yanlei on 2016/4/27.
 */
public class MyView extends View {
    public float X = 50;
    public float Y = 50;
    //对外以公开树叶的宽度和高度
    public static double myViewH;
    public static double myViewW;
    static private int drawableInt;
    //创建画笔
    Paint paint = new Paint();

    public MyView(Context context, int drawable) {
        super(context);
        drawableInt = drawable;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //paint.setColor(Color.YELLOW);
        //canvas.drawCircle(X, Y, 50, paint);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawableInt);
        Matrix matrix = new Matrix();

        matrix.postScale(0.08f, 0.08f);
        myViewH = 0.08 * bmp.getHeight();
        myViewW = 0.08 * bmp.getWidth();
        // 得到新的图片
        Bitmap newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix,
                true);
        canvas.drawBitmap(newbmp, 0, 0, paint);
    }

    public void reDrow(int drawable) {
        drawableInt = drawable;
        this.postInvalidate();
    }

}