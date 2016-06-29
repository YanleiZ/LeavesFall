package com.yanlei.fallingLeaves;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.yanlei.fallingLeaves.common.GMEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameRankingActivity extends AppCompatActivity {
    private Cursor cursor = null;
    private ListView rankinglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking);
        rankinglist = (ListView) findViewById(R.id.ranking);
        Cursor cur = GMEngine.db.rawQuery("SELECT * FROM GameRanking", null);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("RankingId", "ID");
        item1.put("score", "SCORE");
        item1.put("playtime", "TIME");
        data.add(item1);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    String nn1 = cur.getString(0);
                    String nn2 = cur.getString(1);
                    String nn3 = cur.getString(2);
                    Log.i("as1", nn1);
                    Log.i("as2", nn2);
                    Log.i("as3", nn3);

                    HashMap<String, Object> item = new HashMap<String, Object>();
                    item.put("RankingId", nn1);
                    item.put("score", nn2);
                    item.put("playtime", nn3);
                    data.add(item);
                } while (cur.moveToNext());
            }
            SimpleAdapter ranklistAdapter = new SimpleAdapter(this, data, R.layout.rankinglist,
                    new String[]{"RankingId", "score", "playtime"}, new int[]{R.id.listid, R.id.score, R.id.time});
            rankinglist.setAdapter(ranklistAdapter);
        } else {
            //
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(GameRankingActivity.this, StartActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
