package com.yanlei.gravitytest;

/**
 * Created by Yanlei on 2016/5/19.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 图片跑马灯，图片无限循环滚动效果控件
 * 图片长宽必须大于手机长宽，否则会报错退出
 */
public class GameBg extends View {
    Bitmap back;
    int nowY = 0;
    int backHeight;
    int vw;
    int vh;
    int speed;
    public static boolean isover = false;
    public GameBg(Context context) {
        super(context);
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
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1);
        // 设置滚动速度
        speed = 10;
        //缩放一下
        back = Bitmap.createBitmap(backMap, 0, 0, w, h, matrix, true);
        backHeight = back.getHeight();
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    // 该函数的作用是使整个窗口客户区无效。窗口的客户区无效意味着需要重绘
                    invalidate();
                } else {

                }
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                if (!isover) {
                    handler.sendEmptyMessage(0x123);
                }
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
//        if (isover) {
//            back.recycle();
//            System.gc();
//        }
        if (nowY + speed >= backHeight) {
            nowY = 0;
        } else {
            nowY += speed;
        }
    }

}