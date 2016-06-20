package com.yanlei.fallingLeaves.adversary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.yanlei.fallingLeaves.R;

/**
 * Created by Yanlei on 2016/6/8.
 */
public class ButterflyView extends View {
    //创建画笔
    Paint paint = new Paint();
    final static int ANIMUPDATE = 12;
    int uIndex = 0;

    Bitmap butterFly[] = {BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly1), BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly2)};
    Matrix matrix = new Matrix();
    //取得缩放之前的图片大小

    public static double butterflyH;
    public static double butterflyW;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ANIMUPDATE) {
                uIndex++;
                if (uIndex > 1) {
                    uIndex = 0;
                }
                postInvalidate();
            }
        }
    };
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
                handler.sendEmptyMessage(ANIMUPDATE);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }
    };

    public ButterflyView(Context context) {
        super(context);
        Matrix matrix = new Matrix();

        matrix.postScale(0.6f, 0.6f);
        int h = butterFly[0].getHeight();
        int w = butterFly[0].getWidth();
        butterFly[0] = Bitmap.createBitmap(butterFly[0], 0, 0, w, h, matrix,
                true);
        butterFly[1] = Bitmap.createBitmap(butterFly[1], 0, 0, w, h, matrix,
                true);

        butterflyH = butterFly[0].getHeight();
        butterflyW = butterFly[0].getWidth();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(butterFly[uIndex], 0, 0, paint);

        r.run();
    }


}
