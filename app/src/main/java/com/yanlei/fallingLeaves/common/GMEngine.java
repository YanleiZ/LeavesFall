package com.yanlei.fallingLeaves.common;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    public static final int SPIRIT_LIST = R.drawable.spritesheet;
    public static float leavesY = 7f;
    public static float leavesX = 2.5f;
    public static float butterflyY;
    public static float butterflyX;
    public static final float MOVE_SPEED = .1f;
    public static float gravityX = 0;
    public static float gravityY = 0;
    public static float gravityZ = 0;
    public static boolean collision = false;
    public static int game_score = 0;
    public static SQLiteDatabase db;
    public static float branchY = -0.5f;
    public static float branchLX = 0f;
    public static float branchRX = 1.7f;
    public static int overint = 0;

    //树叶的损坏值
    public static int damage_value = 0;
    //背景音乐打开标记
    public static boolean MUSIC_SWITCH = true;
    public static final int PLAYER_FRAMES_BETWEEN_ANI = 5;
    public static boolean gameOver = false;

    /*Kill game and exit*/
    public boolean onExit(View v) {
        try {
            Intent bgMusic = new Intent(context, BackgroundMusic.class);
            context.stopService(bgMusic);
            musicThread.stop();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
