package com.yanlei.fallingLeaves;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wandoujia.ads.sdk.Ads;
import com.yanlei.fallingLeaves.common.GMEngine;
import com.yanlei.fallingLeaves.common.GameDBOpenHelper;

public class StartActivity extends Activity {
    private Button start_btn;
    private Button list_btn;
    private Button exit_btn;
    private Switch music_switch;
    GameDBOpenHelper myDBHelp;
    //ads
    private static final String APP_ID = "100041494";
    private static final String SECRET_KEY = "f08850a72c157de2be6433e63d0b8018";

    private static final String BANNER = "b0e1fc1ce9838002bb341083aa43d3d1";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final GMEngine engine = new GMEngine();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        GMEngine.gameOver = false;
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
                boolean clean;
                //clean= engine.onExit(v);
                clean = true;
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
                startActivity(new Intent(StartActivity.this, GameRankingActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
        myDBHelp = new GameDBOpenHelper(StartActivity.this, "GameRanking.db", null, 1);
        GMEngine.db = myDBHelp.getWritableDatabase();
        //ads
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Ads.init(StartActivity.this, APP_ID, SECRET_KEY);
                    return true;
                } catch (Exception e) {
                    Log.e("ads-sample", "error", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                final ViewGroup container = (ViewGroup) findViewById(R.id.banner_container);

                if (success) {
                    /**
                     * pre load
                     */
                    Ads.preLoad(BANNER, Ads.AdFormat.banner);


                    /**
                     * add ad views
                     */
                    View bannerView = Ads.createBannerView(StartActivity.this, BANNER);
                    container.addView(bannerView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                } else {
                    TextView errorMsg = new TextView(StartActivity.this);
                    errorMsg.setText("init failed");
                    container.addView(errorMsg);
                }
            }
        }.execute();
        //
    }

    //保存点击的时间
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出游戏",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
