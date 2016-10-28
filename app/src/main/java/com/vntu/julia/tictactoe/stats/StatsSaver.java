package com.vntu.julia.tictactoe.stats;


import android.content.Context;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatsSaver {

    private DBConnector dbConnector;
    public StatsSaver(Context context) {
        dbConnector = new DBConnector(context);
    }
    public void persist(StatsDTO dto) { dbConnector.insert(dto);}
}
