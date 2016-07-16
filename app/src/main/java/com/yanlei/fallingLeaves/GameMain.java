package com.yanlei.fallingLeaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanlei.fallingLeaves.common.GMEngine;

public class GameMain extends Activity {
    private GameView gameView;
    private MyHandler handler = null;

    public static Handler_a mAPP = null;
    TextView tv;
    ImageView iv;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getPosition();
        mAPP = new Handler_a();
        handler = new MyHandler();
        mAPP.setHandler(handler);
        //布局
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //生命值
        iv = new ImageView(this);
        RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(450, 100);
        ivParams.addRule(RelativeLayout.ALIGN_RIGHT);
        iv.setImageResource(R.mipmap.hp5);
        iv.setLayoutParams(ivParams);

        linearLayout.addView(iv);
        //得分
        tv = new TextView(this);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        tv.setTextSize(17);
        tv.setTextColor(Color.YELLOW);
        tv.setText("当前分数：" + " " + "" + GMEngine.game_score);
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvParams.addRule(RelativeLayout.ALIGN_LEFT);
        tv.setLayoutParams(tvParams);
        linearLayout.addView(tv);

        RelativeLayout.LayoutParams allLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addContentView(linearLayout, allLayoutParams);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        gameView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    public void getPosition() {
        //获得重力感应硬件控制器
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //添加重力感应侦听，并实现其方法，
        SensorEventListener sel = new SensorEventListener() {
            public void onSensorChanged(SensorEvent se) {
                GMEngine.gravityX = se.values[SensorManager.DATA_X];
                GMEngine.gravityY = se.values[SensorManager.DATA_Y];
                GMEngine.gravityZ = se.values[SensorManager.DATA_Z];
            }

            public void onAccuracyChanged(Sensor arg0, int arg1) {

            }
        };
        //注册Listener，SENSOR_DELAY_GAME为检测的精确度，
        sm.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void gameOver() {
        new AlertDialog.Builder(GameMain.this, AlertDialog.BUTTON_POSITIVE).setTitle("ＧＡＭＥ　ＯＶＥＲ").setMessage("您的得分为：" + GMEngine.game_score + "，查看分数排名？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(GameMain.this, GameRankingActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        GMEngine.gameOver = false;
                        GMEngine.game_score = 0;
                        GMEngine.overint = 0;


                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(GameMain.this, StartActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        GMEngine.gameOver = false;
                        GMEngine.game_score = 0;
                        GMEngine.overint = 0;


                    }
                }).setCancelable(false).setIcon(R.drawable.shuye3).show();
    }

    final class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) { // 更新UI
                iv.setImageResource(R.mipmap.hp0);
                gameOver();
            } else if (msg.what == 2) {
                tv.setText("当前分数：" + " " + GMEngine.game_score);
            } else if (msg.what == 31) {
                iv.setImageResource(R.mipmap.hp4);
            } else if (msg.what == 32) {
                iv.setImageResource(R.mipmap.hp3);
            } else if (msg.what == 33) {
                iv.setImageResource(R.mipmap.hp2);
            } else if (msg.what == 34) {
                iv.setImageResource(R.mipmap.hp1);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
