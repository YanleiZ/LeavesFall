package com.yanlei.gravitytest;

/**
 * Created by Yanlei on 2016/5/19.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 图片跑马灯，图片无限循环滚动效果控件
 * 图片长宽必须大于手机长宽，否则会报错退出
 */
public class GameView extends View {
    Bitmap back;

    int nowY = 0;
    int backHeight;
    int vw;
    int vh;
    int speed;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 启动
    public void Start(Bitmap backMap, Window windows) {

        int h = backMap.getHeight();
        int w = backMap.getWidth();

        // 获取设备高度和宽度
        Rect frame = new Rect();
        windows.getDecorView().getWindowVisibleDisplayFrame(frame);
        vh = frame.height();
        vw = frame.width();
        // 计算缩放比例
        float scaleWidth = ((float) vw) / w;
        float scaleHeight = ((float) vh) / h;

        // 取得想要缩放的matrix参数

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1);
        // 设置滚动速度
        speed = 10;
        //裁剪一下
        // back = Bitmap.createBitmap(backMap, 0, 0, backMap.getWidth(), vh);
        //缩放一下
        back = Bitmap.createBitmap(backMap, 0, 0, w, h, matrix, true);
        backHeight = back.getHeight();

        final Handler handler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    // 该函数的作用是使整个窗口客户区无效。窗口的客户区无效意味着需要重绘
                    invalidate();
                }
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int h = backHeight - nowY;

        if (vh <= h) {
            // 图片剩余宽度大于屏幕宽度，从原图上截取屏幕窗口大小的一块区域
            Bitmap bitmap = Bitmap.createBitmap(back, 0, nowY, vw, vh);
            canvas.drawBitmap(bitmap, 0, 0, null);
        } else {
            Bitmap bitmap = Bitmap.createBitmap(back, 0, nowY, vw, h);
            canvas.drawBitmap(bitmap, 0, 0, null);
            Bitmap bitmap2 = Bitmap.createBitmap(back, 0, 0, vw, vh - h);
            canvas.drawBitmap(bitmap2, 0, h, null);
        }

        if (nowY + speed >= backHeight) {
            nowY = 0;
        } else {
            nowY += speed;
        }
    }

}