package com.vntu.julia.tictactoe.stats;


import com.vntu.julia.tictactoe.game.Player;

import java.util.Date;
import java.util.List;

//просто інтерфейс для отримувача даних з бази
//можна видалити, але тоді треба перейменувати DBStatsFetcher у StatsFetcher
//і видалити там анотацію @Override
public interface StatsFetcher {
    List<Date> getResults(Player player);
}
