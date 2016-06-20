package com.yanlei.fallingLeaves;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.yanlei.fallingLeaves.common.GMEngine;

/**
 * Created by Yanlei on 2016/6/15.
 */
public class GameView extends GLSurfaceView {
    private GameRender renderer;

    public GameView(Context context) {
        super(context);
        GMEngine.context = context;
        renderer = new GameRender();

        this.setRenderer(renderer);
    }
}
