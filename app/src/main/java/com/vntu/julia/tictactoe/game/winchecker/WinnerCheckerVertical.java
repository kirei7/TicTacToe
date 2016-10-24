package com.vntu.julia.tictactoe.game.winchecker;


import com.vntu.julia.tictactoe.game.Game;
import com.vntu.julia.tictactoe.game.Player;
import com.vntu.julia.tictactoe.game.Square;

public class WinnerCheckerVertical implements IWinnerChecker {
    private Game game;

    public WinnerCheckerVertical (Game game) {
        this.game = game;
    }
    public Player checkWinner() {
        Square[][] field = game.getField();
        Player currPlayer;
        Player lastPlayer = null;
        for (int i = 0, len = field.length; i < len; i++) {
            lastPlayer = null;
            int successCounter = 1;
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                currPlayer = field[j][i].getPlayer();
                if (currPlayer == lastPlayer && (currPlayer != null && lastPlayer !=null)) {
                    successCounter++;
                    if (successCounter == len2) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        return null;
    }
}