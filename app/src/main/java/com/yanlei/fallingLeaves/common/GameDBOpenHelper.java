package com.yanlei.fallingLeaves.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yanlei on 2016/6/20.
 */
public class GameDBOpenHelper extends SQLiteOpenHelper {
    public GameDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GameRanking(RankingId INTEGER PRIMARY KEY AUTOINCREMENT,score INTEGER,[CreatedTime] playtime NOT NULL DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
