package com.vntu.julia.tictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vntu.julia.tictactoe.game.Player;

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
        StatsFetcher statsFetcher = new MockStatsFetcher();

        ListView xStatsView = (ListView) findViewById(R.id.scorePlayerX);
        ListView oStatsView = (ListView) findViewById(R.id.scorePlayerO);

        List<String> xStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("PlayerX"))
        );
        List<String> oStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("PlayerO"))
        );

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
