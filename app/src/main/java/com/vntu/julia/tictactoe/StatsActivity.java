package com.vntu.julia.tictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vntu.julia.tictactoe.R;
import com.vntu.julia.tictactoe.game.Player;
import com.vntu.julia.tictactoe.stats.DBStatsFetcher;
import com.vntu.julia.tictactoe.stats.MockStatsFetcher;
import com.vntu.julia.tictactoe.stats.StatsFetcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        StatsFetcher statsFetcher = new DBStatsFetcher(this);

        ListView xStatsView = (ListView) findViewById(R.id.scoresCountPlayerX);
        ListView oStatsView = (ListView) findViewById(R.id.scoresCountPlayerO);

        List<String> xStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("playerX"))
        );
        List<String> oStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("playerO"))
        );

        ((TextView) findViewById(R.id.scorePlayerX)).setText(xStats.size() + " wins");
        ((TextView) findViewById(R.id.scorePlayerO)).setText(oStats.size() + " wins");

        ArrayAdapter<String> xStatsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                xStats
                 );
        ArrayAdapter<String> oStatsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                oStats
                 );
        xStatsView.setAdapter(xStatsAdapter);
            xStatsView.setAdapter(xStatsAdapter);
            oStatsView.setAdapter(oStatsAdapter);
    }

    public static class StatsFormater {
        public static List<String> getFormatedStrings(List<Date> dates) {
            List<String> strings = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            for(Date date : dates) {
                strings.add(
                        dateFormat.format(date)
                );
            }
            return strings;
        }
    }
}
