package com.yanlei.fallingLeaves.common;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusic extends Service {
    public BackgroundMusic() {
    }
    public static boolean isRunning = false;
    static MediaPlayer player;
    private static Context context;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        setMusicOptions(GMEngine.LOOP_BACKGROUND_MUSIC,GMEngine.R_VOLUME,GMEngine.L_VOLUME,GMEngine.SPLASH_SCREEN_MUSIC);
    }
    public static void setMusicOptions(boolean isLooped, int rVolume, int lVolume, int soundFile){
        player = MediaPlayer.create(context, soundFile);
        player.setLooping(isLooped);
        player.setVolume(rVolume,lVolume);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        try
        {
            player.start();
            isRunning = true;
        }catch(Exception e){
            isRunning = false;
            player.stop();
        }

        return 1;
    }
    public void onStart(Intent intent, int startId) {
        // TODO
    }
    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onStop() {
        isRunning = false;
        player.stop();
    }
    public void onPause() {      }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
    @Override
    public void onLowMemory() {
        player.stop();
    }

}

