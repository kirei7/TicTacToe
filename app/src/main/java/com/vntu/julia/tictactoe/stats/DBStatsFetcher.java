package com.vntu.julia.tictactoe.stats;


import android.content.Context;

import com.vntu.julia.tictactoe.game.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBStatsFetcher implements StatsFetcher {

    private DBConnector dbConnector;

    public DBStatsFetcher(Context context) {
        dbConnector = new DBConnector(context);
    }

    @Override
    public List<Date> getResults(Player player) {
        List<StatsDTO> stats = dbConnector.selectAll();
        List<Date> dates = new ArrayList<>();
        for(StatsDTO dto : stats) {
            Logger.getLogger("TESTLOG").log(Level.WARNING, player.getName() + " " + dto.getName());
            if (player.getName().equals(dto.getName())) {
                dates.add(new Date(dto.getDate()));
            }
        }

        return dates;
    }
}
