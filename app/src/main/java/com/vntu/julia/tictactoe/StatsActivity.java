package com.vntu.julia.tictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vntu.julia.tictactoe.game.Player;
import com.vntu.julia.tictactoe.stats.DBStatsFetcher;
import com.vntu.julia.tictactoe.stats.StatsFetcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*екран для відображення статистики*/
public class StatsActivity extends Activity {

    //при створенні
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //задаємо вигляд за описом у файлі activity_stats
        setContentView(R.layout.activity_stats);
        //об'єкт для отримання даних статистики з бази даних
        StatsFetcher statsFetcher = new DBStatsFetcher(this);

        /*два вигляди, що будуть відображати статистику для кожного з гравців
        ListView - вигляд, що відображає елементи списком, в нашому випадку
        це буде список з датами перемог*/
        // для гравця х
        ListView xStatsView = (ListView) findViewById(R.id.scoresCountPlayerX);
        //для гравця о
        ListView oStatsView = (ListView) findViewById(R.id.scoresCountPlayerO);

        //створюємо два списки, кожен з яких зберігає дані по відповідному гравцю
        //дати у списках форматуються методом getFormatedStrings() - див. нижче
        List<String> xStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("playerX"))
        );
        List<String> oStats = StatsFormater.getFormatedStrings(
                statsFetcher.getResults(new Player("playerO"))
        );

        //просто підписи до кожного списку типу "Х wins"
        ((TextView) findViewById(R.id.scorePlayerX)).setText(xStats.size() + " wins");
        ((TextView) findViewById(R.id.scorePlayerO)).setText(oStats.size() + " wins");

        //адаптери необхідні для відображення спискової інформації у ListView
        //тут ми "обгортаємо" кожен список своїм адаптером
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
        //вставляємо у кожен вигляд по адаптеру
        xStatsView.setAdapter(xStatsAdapter);
        oStatsView.setAdapter(oStatsAdapter);
    }

    //клас для форматування дати при виводі
    public static class StatsFormater {
        //метод отримує список дат і повертає список строк з датами
        public static List<String> getFormatedStrings(List<Date> dates) {
            //список що буде повертатись
            List<String> strings = new ArrayList<>();
            //об'єкт форматування дати(дата буде форматуватись за шаблоном yyyy/MM/dd HH:mm
            //типу "рік/місяць/день година:хвилина"
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            //конвертуємо кожну дату у строку і заносимо строку в список
            for(Date date : dates) {
                strings.add(
                        dateFormat.format(date)
                );
            }
            //повертаємо список
            return strings;
        }
    }
}
