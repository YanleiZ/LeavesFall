package com.yanlei.fallingLeaves.common;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;

import com.yanlei.fallingLeaves.R;

/**
 * Created by Yanlei on 2016/6/15.
 */
public class GMEngine {
    public static final int SPLASH_SCREEN_MUSIC = R.raw.bgmusic;
    public static final int R_VOLUME = 100;
    public static final int L_VOLUME = 100;
    public static final boolean LOOP_BACKGROUND_MUSIC = true;
    public static final int GAME_THREAD_FPS_SLEEP = (1000 / 60);
    public static Thread musicThread;
    public static Context context;
    public static Display display;
    public static float SCROLL_BACKGROUND_1 = -.002f;
    public static float SCROLL_BACKGROUND_2 = -.005f;
    public static final int BACKGROUND_LAYER_ONE = R.drawable.sky;
    public static final int BACKGROUND_LAYER_TWO = R.drawable.tree;
    public static final int SPIRIT_LIST = R.drawable.shuye;
    public static boolean GAMEOVER = false;
    public static float leavesY = 7f;
    public static float leavesX = 2.5f;
    public static float butterflyY;
    public static float butterflyX;
    public static final float MOVE_SPEED = .1f;
    public static float gravityX = 0;
    public static float gravityY = 0;
    public static float gravityZ = 0;
    public static float branchY = 0f;

    //树叶的损坏值
    public static int damage_value = 0;
    //背景音乐打开标记
    public static boolean MUSIC_SWITCH = true;
    public static final int PLAYER_FRAMES_BETWEEN_ANI = 5;

    /*Kill game and exit*/
    public boolean onExit(View v) {
        try {
            Intent bgmusic = new Intent(context, com.yanlei.fallingLeaves.common.BackgroundMusic.class);
            context.stopService(bgmusic);
            musicThread.stop();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
