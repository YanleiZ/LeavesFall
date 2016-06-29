package com.yanlei.fallingLeaves;

import android.app.Application;

/**
 * Created by Yanlei on 2016/6/20.
 */
public class Handler_a extends Application {

    // 共享变量
    private GameMain.MyHandler handler = null;

    // set方法
    public void setHandler(GameMain.MyHandler handler) {
        this.handler = handler;
    }

    // get方法
    public GameMain.MyHandler getHandler() {
        return handler;
    }
}