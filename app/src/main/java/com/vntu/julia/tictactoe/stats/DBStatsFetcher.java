package com.vntu.julia.tictactoe.stats;


import android.content.Context;

import com.vntu.julia.tictactoe.game.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//клас для отримання записів з бд
public class DBStatsFetcher implements StatsFetcher {

    private DBConnector dbConnector;

    public DBStatsFetcher(Context context) {
        dbConnector = new DBConnector(context);
    }

    //вертаємо всі дати перемог визначеного гравця
    @Override
    public List<Date> getResults(Player player) {
        //просто отримуємо всі данні з бд
        List<StatsDTO> stats = dbConnector.selectAll();
        //створюємо список для зберігання дат визначеного гравця
        List<Date> dates = new ArrayList<>();
        //пробігаємось по всім даним з бд
        for(StatsDTO dto : stats) {
            //якщо запис стосується визначеного гравця то додаємо запис у список(запис зберігає лише дату,
            //тому й у список додаємо лише дату
            if (player.getName().equals(dto.getName())) {
                dates.add(new Date(dto.getDate()));
            }
        }
        return dates;
    }
}
