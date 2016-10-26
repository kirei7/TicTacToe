package com.vntu.julia.tictactoe;


import com.vntu.julia.tictactoe.game.Player;

import java.util.Date;
import java.util.List;

public interface StatsFetcher {
    List<Date> getResults(Player player);
}
