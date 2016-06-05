package com.yanlei.gravitytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private TextView textView;
    private float x, y, z;
    private MyView myView;
    BarrierView bv;
    private static float x_x, x_y;
    //private static float i1 = 1, i2 = 1, i3 = 2, i4 = 2;
    private RelativeLayout im;
    //屏幕的高度和宽度
    public static float width;
    public static float height;
    private GameView gameView;
    private static double bvLocationY;
    private static int toastTag = 0;
    private boolean fall = true;
    static int shuyetag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = (WindowManager) MainActivity.this
                .getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        bvLocationY = height + BarrierView.shuzhiH;
        textView = (TextView) findViewById(R.id.textView);
        im = (RelativeLayout) findViewById(R.id.re);

        gameView = (GameView) findViewById(R.id.gameview);

        Drawable d = getDrawable(R.drawable.tree4);
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        gameView.Start(bm, getWindow());
        myView = new MyView(MainActivity.this, R.drawable.shuye1);
        bv = new BarrierView(MainActivity.this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTask(), 1, 50);
        timer.scheduleAtFixedRate(new UpdateShuyeTask(), 4000, 4000);
        im.addView(bv);
        im.addView(myView);
        //bv.setX(x_x);
        //bv.setY(x_y);


        x_x = width / 2;
        x_y = height / 4;
        myView.setX(x_x);
        myView.setY(x_y);

        //获得重力感应硬件控制器
        SensorManager sm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //添加重力感应侦听，并实现其方法，
        SensorEventListener sel = new SensorEventListener() {
            public void onSensorChanged(SensorEvent se) {
                x = se.values[SensorManager.DATA_X];
                y = se.values[SensorManager.DATA_Y];
                z = se.values[SensorManager.DATA_Z];
                float tempx = x_x;
                float tempy = x_y;

                if (x <= -3) {

                    x_x += 2;
                    if (x_x + MyView.myViewW >= width) {
                        x_x = x_x - 2;
                    }
                }

                if (x >= 3) {

                    x_x -= 2;
                    if (x_x <= 0) {
                        x_x = 0;
                    }
                }
                if (fall) {
                    if (y <= -3) {
                        //  i3 += 0.1;
                        x_y -= 2;
                        if (x_y <= 0) {
                            x_y = 0;
                        }
                    }
                    if (y >= 3) {
                        //i4 += 0.1;
                        x_y += 2;
                        if (x_y + MyView.myViewH >= height) {
                            x_y = x_y - 2;
                        }
                    }
                }
                if ((x_x >= 0 & x_x <= BarrierView.shuzhiW) && (x_y >= bvLocationY && x_y <= bvLocationY + BarrierView.shuzhiH)) {
                    if (toastTag == 0) {
                        toastTag = 1;
                        Toast.makeText(MainActivity.this, "碰撞！", Toast.LENGTH_SHORT).show();
                    }
                    x_x = tempx;
                    x_y = (float) bvLocationY;
                    myView.setX(x_x);
                    myView.setY(x_y);
                } else {

                    myView.setX(x_x);
                    myView.setY(x_y);
                    if (toastTag == 1) {
                        toastTag = 0;
                        Toast.makeText(MainActivity.this, "未碰撞~~", Toast.LENGTH_SHORT).show();
                    }
                }


//                boolean luodi = false;
//                if (luodi) {


                //    }

                textView.setText("x=" + (int) x + "\ny=" + (int) y + "\nz=" + (int) z);

            }

            public void onAccuracyChanged(Sensor arg0, int arg1) {

            }
        };
        //注册Listener，SENSOR_DELAY_GAME为检测的精确度，
        sm.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private Handler upDateHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bvLocationY -= 5;
                    if (bvLocationY < -BarrierView.shuzhiH) {
                        bvLocationY = height + BarrierView.shuzhiH;
                    }
                    bv.setY((float) bvLocationY);
                    break;
            }
        }
    };
    private Handler upDataShuyeHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    myView.reDrow(R.drawable.shuye2);
                    shuyetag++;
                    break;
                case 1:
                    myView.reDrow(R.drawable.shuye3);
                    shuyetag++;
                    break;
                case 2:
                    myView.reDrow(R.drawable.shuye4);
                    shuyetag++;
                    break;
                case 3:
                    myView.reDrow(R.drawable.shuye5);
                    shuyetag++;
                    break;
                case 4:
                    new AlertDialog.Builder(MainActivity.this).setTitle("确认").setMessage("确定吗？")
                            .setPositiveButton("是", null)
                            .setNegativeButton("否", null).show();

            }
        }
    };

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                //System.exit(0);
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            upDateHandler.sendMessage(message);

        }
    }

    private class UpdateShuyeTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = shuyetag;
            upDataShuyeHandler.sendMessage(message);

        }
    }
}

