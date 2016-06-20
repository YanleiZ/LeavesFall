package com.yanlei.fallingLeaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.yanlei.fallingLeaves.common.GMEngine;

public class StartActivity extends Activity {
    private Button start_btn;
    private Button list_btn;
    private Button exit_btn;
    private Switch music_switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final GMEngine engine = new GMEngine();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (GMEngine.MUSIC_SWITCH) {
            com.yanlei.fallingLeaves.common.GMEngine.musicThread = new Thread() {
                public void run() {
                    Intent bgmusic = new Intent(getApplicationContext(), com.yanlei.fallingLeaves.common.BackgroundMusic.class);
                    startService(bgmusic);

                }
            };
            GMEngine.musicThread.start();
        }
        music_switch = (Switch) findViewById(R.id.musicswitch);
        if (GMEngine.MUSIC_SWITCH) {
            music_switch.setChecked(true);
        } else {
            music_switch.setChecked(false);
        }
        music_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    GMEngine.MUSIC_SWITCH = true;
                    com.yanlei.fallingLeaves.common.GMEngine.musicThread = new Thread() {
                        public void run() {
                            Intent bgmusic = new Intent(getApplicationContext(), com.yanlei.fallingLeaves.common.BackgroundMusic.class);
                            startService(bgmusic);

                        }
                    };
                    GMEngine.musicThread.start();
                } else {
                    GMEngine.MUSIC_SWITCH = false;
                    Intent bgmusic = new Intent(getApplicationContext(), com.yanlei.fallingLeaves.common.BackgroundMusic.class);
                    getApplicationContext().stopService(bgmusic);
                }
            }
        });
        start_btn = (Button) findViewById(R.id.startbtn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        startActivity(new Intent(StartActivity.this, GameMain.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }.run();

            }
        });
        exit_btn = (Button) findViewById(R.id.exitbtn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clean = false;
                clean = engine.onExit(v);
                if (clean) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }

            }
        });
        list_btn = (Button) findViewById(R.id.listbtn);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
