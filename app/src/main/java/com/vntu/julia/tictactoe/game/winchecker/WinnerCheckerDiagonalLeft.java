package com.vntu.julia.tictactoe.game.winchecker;


import com.vntu.julia.tictactoe.game.Game;
import com.vntu.julia.tictactoe.game.Player;
import com.vntu.julia.tictactoe.game.Square;

public class WinnerCheckerDiagonalLeft implements IWinnerChecker {
    private Game game;

    public WinnerCheckerDiagonalLeft(Game game) {
        this.game = game;
    }

    public Player checkWinner() {
        Square[][] field = game.getField();
        Player currPlayer;
        Player lastPlayer = null;
        int successCounter = 1;
        for (int i = 0, len = field.length; i < len; i++) {
            currPlayer = field[i][i].getPlayer();
            if (currPlayer != null) {
                if (lastPlayer == currPlayer) {
                    successCounter++;
                    if (successCounter == len) {
                        return currPlayer;
                    }
                }
            }
            lastPlayer = currPlayer;
        }
        return null;
    }
}