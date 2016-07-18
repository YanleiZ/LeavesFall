package com.yanlei.fallingLeaves;

import android.content.ContentValues;
import android.opengl.GLSurfaceView.Renderer;

import com.yanlei.fallingLeaves.adversary.Branch;
import com.yanlei.fallingLeaves.adversary.Butterfly;
import com.yanlei.fallingLeaves.common.GMEngine;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Yanlei on 2016/6/15.
 */
public class GameRender implements Renderer {
    private GMBackground background = new GMBackground();
    private GMBackground background2 = new GMBackground();
    private Leaves leaves = new Leaves();
    private Branch branch = new Branch();
    private Butterfly butterfly = new Butterfly();
    private float bgScroll1;
    private float bgScroll2;
    private int leaves_frams = 0;
    private int butterfly_frams = 0;
    private int butterflyType = 0;
    private float sheetX = 0;
    private float sheetY = 0;
    private static int branchtype = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(GMEngine.GAME_THREAD_FPS_SLEEP);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        scrollBackground1(gl);
        scrollBackground2(gl);

        movePlayer(gl);
        moveBranch(gl);
        moveButterfly(gl);

        detectCollisions();

        gl.glEnable(GL10.GL_BLEND);
        //gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

    }

    private void scrollBackground1(GL10 gl) {
        if (bgScroll1 == Float.MAX_VALUE) {
            bgScroll1 = 0f;
        }


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, bgScroll1, 0.0f);
        background.draw(gl);
        gl.glPopMatrix();
        if (!GMEngine.gameOver) {
            bgScroll1 += GMEngine.SCROLL_BACKGROUND_1;
        }
        gl.glLoadIdentity();
    }

    private void scrollBackground2(GL10 gl) {
        if (bgScroll2 == Float.MAX_VALUE) {
            bgScroll2 = 0f;
        }
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, bgScroll2, 0.0f);
        background2.draw(gl);
        gl.glPopMatrix();
        if (!GMEngine.gameOver) {
            bgScroll2 += GMEngine.SCROLL_BACKGROUND_2;
        }
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glEnable(GL10.GL_BLEND);
        //gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        background.loadTexture(gl, GMEngine.BACKGROUND_LAYER_ONE, GMEngine.context);
        background2.loadTexture(gl, GMEngine.BACKGROUND_LAYER_TWO, GMEngine.context);
        leaves.loadTexture(gl, GMEngine.SPIRIT_LIST, GMEngine.context);
        branch.loadTexture(gl, R.drawable.shuzhisheet, GMEngine.context);
        butterfly.loadTexture(gl, GMEngine.SPIRIT_LIST, GMEngine.context);
    }

    public void movePlayer(GL10 gl) {
        switch ((int) GMEngine.gravityX) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                if (GMEngine.leavesX > 0 && leaves_frams >= GMEngine.PLAYER_FRAMES_BETWEEN_ANI) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    //缩放
                    gl.glScalef(.15f, .09f, 1f);
                    GMEngine.leavesX -= GMEngine.MOVE_SPEED;
                    gl.glTranslatef(GMEngine.leavesX, GMEngine.leavesY, 0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(sheetX, sheetY, 0.0f);
                    leaves.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    leaves_frams = 0;
                } else {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    //缩放
                    gl.glScalef(.15f, .09f, 1f);

                    gl.glTranslatef(GMEngine.leavesX, GMEngine.leavesY, 0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(sheetX, sheetY, 0.0f);
                    leaves.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    leaves_frams += 1;
                }
                break;
            case -2:
            case -3:
            case -4:
            case -5:
            case -6:
            case -7:
            case -8:
            case -9:
            case -10:
                if (GMEngine.leavesX < (10 / 1.5) - 1 && leaves_frams >= GMEngine.PLAYER_FRAMES_BETWEEN_ANI) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    //缩放
                    gl.glScalef(.15f, .09f, 1f);
                    GMEngine.leavesX += GMEngine.MOVE_SPEED;
                    gl.glTranslatef(GMEngine.leavesX, GMEngine.leavesY, 0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(sheetX, sheetY, 0.0f);
                    leaves.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    leaves_frams = 0;
                } else {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    //缩放
                    gl.glScalef(.15f, .09f, 1f);

                    gl.glTranslatef(GMEngine.leavesX, GMEngine.leavesY, 0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(sheetX, sheetY, 0.0f);
                    leaves.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    leaves_frams += 1;
                }
                break;
            default:

                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                //缩放
                gl.glScalef(.15f, .09f, 1f);
                gl.glTranslatef(GMEngine.leavesX, GMEngine.leavesY, 0f);
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(sheetX, sheetY, 0.0f);
                leaves.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                leaves_frams = 0;
        }
    }

    public void moveBranch(GL10 gl) {
        if (!GMEngine.gameOver) {
            if (bgScroll2 == Float.MAX_VALUE) {
                bgScroll2 = 0f;
            }
            if (GMEngine.branchY <= 10 / 2) {
                if (branchtype == 0) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    gl.glScalef(.27f, .2f, 1f);
                    GMEngine.branchY += .01f;
                    gl.glTranslatef(0f, GMEngine.branchY, 0f);

                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f, 0.0f, 0.0f);
                    branch.draw(gl);
                    gl.glPopMatrix();

                    gl.glLoadIdentity();
                } else {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    gl.glScalef(.27f, .2f, 1f);
                    GMEngine.branchY += .01f;
                    gl.glTranslatef(1.7f, GMEngine.branchY, 0f);

                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.5f, 0.0f, 0.0f);
                    branch.draw(gl);
                    gl.glPopMatrix();

                    gl.glLoadIdentity();
                }
            } else {
                GMEngine.game_score += 15;
                GameMain.MyHandler mHandler3 = GameMain.mAPP.getHandler();
                mHandler3.sendEmptyMessage(2);
                GMEngine.branchY = -0.5f;
                if (branchtype == 0) {
                    branchtype = 1;
                } else {
                    branchtype = 0;
                }

            }
        } else if (GMEngine.gameOver && GMEngine.overint == 0) {
            GameMain.MyHandler mHandler2 = GameMain.mAPP.getHandler();
            mHandler2.sendEmptyMessage(1);
            ContentValues values1 = new ContentValues();
            values1.put("score", GMEngine.game_score);
            GMEngine.db.insert("GameRanking", "错误！", values1);
            GMEngine.branchY = -0.5f;
            GMEngine.overint++;
        }
    }

    public void moveButterfly(GL10 gl) {
        if (!GMEngine.gameOver) {
            if (butterfly_frams >= 8) {
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                //缩放
                gl.glScalef(.15f, .09f, 1f);

                gl.glTranslatef(GMEngine.butterflyX, GMEngine.butterflyY, 0f);
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.25f, 0.25f, 0.0f);
                butterfly.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                if (butterfly_frams < 16) {
                    butterfly_frams += 1;
                } else {
                    butterfly_frams = 0;
                }
                if (GMEngine.leavesY - GMEngine.butterflyY <= 0.5) {
                    GMEngine.butterflyY += 0.01;
                    GMEngine.butterflyX += 0.01;
                } else {
                    GMEngine.butterflyX += 0.01 * (GMEngine.leavesX - GMEngine.butterflyX) / (GMEngine.leavesY - GMEngine.butterflyY);
                    GMEngine.butterflyY += 0.01;
                }

            } else if (butterfly_frams < 8) {
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                //缩放
                gl.glScalef(.15f, .09f, 1f);
                if (GMEngine.leavesY - GMEngine.butterflyY <= 1) {
                    GMEngine.butterflyY += 0.01;
                    GMEngine.butterflyX += 0.01;
                } else {
                    GMEngine.butterflyX += 0.01 * (GMEngine.leavesX - GMEngine.butterflyX) / (GMEngine.leavesY - GMEngine.butterflyY);
                    GMEngine.butterflyY += 0.01;
                }
                gl.glTranslatef(GMEngine.butterflyX, GMEngine.butterflyY, 0f);
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.50f, 0.25f, 0.0f);
                butterfly.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                butterfly_frams += 1;
                if (GMEngine.butterflyX <= -1 || GMEngine.butterflyX >= (10 / 1.5) || GMEngine.butterflyY > 10 / 0.9) {
                    GMEngine.butterflyX = new Random().nextInt(6);
                    GMEngine.butterflyY = 0;
                    GMEngine.game_score += 10;
                    GameMain.MyHandler mHandler1 = GameMain.mAPP.getHandler();
                    mHandler1.sendEmptyMessage(2);
                }
                if (GMEngine.collision) {
                    GMEngine.butterflyX = 0;
                    GMEngine.butterflyY = 0;
                    if (sheetX == 0 && sheetY == 0) {
                        sheetX += .25;
                        //发送消息改变生命值
                        GameMain.MyHandler mHandler4_1 = GameMain.mAPP.getHandler();
                        mHandler4_1.sendEmptyMessage(31);
                    } else if (sheetX - 0.25f <= 0.0001  && sheetY == 0) {
                        sheetX += .25;
                        //发送消息改变生命值
                        GameMain.MyHandler mHandler4_2 = GameMain.mAPP.getHandler();
                        mHandler4_2.sendEmptyMessage(32);

                    } else if ( sheetX - .50f <= .0001 && sheetY == 0) {
                        sheetX += .25;
                        //发送消息改变生命值
                        GameMain.MyHandler mHandler4_2 = GameMain.mAPP.getHandler();
                        mHandler4_2.sendEmptyMessage(33);

                    }else if (sheetX - .75 <= 0.001 && sheetY == 0) {
                        sheetX = 0f;
                        sheetY = 0.26f;
                        //发送消息改变生命值
                        GameMain.MyHandler mHandler4_3 = GameMain.mAPP.getHandler();
                        mHandler4_3.sendEmptyMessage(34);
                    } else {
                        GMEngine.gameOver = true;
                        GMEngine.branchY = -0.5f;
                        GameMain.MyHandler mHandler5 = GameMain.mAPP.getHandler();
                        mHandler5.sendEmptyMessage(1);
                        GMEngine.branchY = -0.5f;
                        GMEngine.overint++;
                        ContentValues values1 = new ContentValues();
                        values1.put("score", GMEngine.game_score);
                        GMEngine.db.insert("GameRanking", "错误！", values1);
                    }
                }
            }
        }

    }

    private void detectCollisions() {
        if (GMEngine.leavesY >= GMEngine.butterflyY - 0.9
                && GMEngine.leavesY <= GMEngine.butterflyY
                && GMEngine.leavesX <= GMEngine.butterflyX + 1
                && GMEngine.leavesX >= GMEngine.butterflyX - 1) {
            GMEngine.collision = true;
        } else {
            GMEngine.collision = false;
        }
        if (branchtype == 0) {
            if (2.25 <= GMEngine.branchY
                    && 3.45 >= GMEngine.branchY
                    && GMEngine.leavesX <= GMEngine.branchLX + 3) {
                GMEngine.gameOver = true;
                GMEngine.branchY = -0.5f;

            }
        } else {
            if (2.25 <= GMEngine.branchY
                    && 3.45 >= GMEngine.branchY
                    && GMEngine.leavesX >= 10 / 2.7 - 1) {
                GMEngine.gameOver = true;
                GMEngine.branchY = -0.5f;

            }
        }
    }

}
