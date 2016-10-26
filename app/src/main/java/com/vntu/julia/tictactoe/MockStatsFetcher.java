package com.vntu.julia.tictactoe;

import com.vntu.julia.tictactoe.game.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MockStatsFetcher implements StatsFetcher {
    private Date date;
    @Overrideі
    public List<Date> getResults(Player player) {
        date = new Date();
        List<Date> dates = new ArrayList<>();
        dates.add(getDate());
        dates.add(getDate());
        dates.add(getDate());
        dates.add(getDate());
        return dates;
    }

    private Date getDate() {
        date.setTime(date.getTime() + 200000);
        return date;
    }
}
