package com.yanlei.fallingLeaves;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.yanlei.fallingLeaves.common.GMEngine;

public class GameMain extends Activity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        getPosition();
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
}
