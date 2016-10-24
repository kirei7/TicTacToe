package com.vntu.julia.tictactoe.game;

import com.vntu.julia.tictactoe.game.winchecker.WinnerCheckerDiagonalLeft;
import com.vntu.julia.tictactoe.game.winchecker.WinnerCheckerDiagonalRight;
import com.vntu.julia.tictactoe.game.winchecker.WinnerCheckerHorizontal;
import com.vntu.julia.tictactoe.game.winchecker.IWinnerChecker;
import com.vntu.julia.tictactoe.game.winchecker.WinnerCheckerVertical;

public class Game implements IWinnerChecker {
        /**
         * игроки
         */
        private Player[] players;
        /**
         * поле
         */
        private Square[][] field;
        /**
         * начата ли игра?
         */
        private boolean started;
        /**
         * текущий игрок
         */
        private Player activePlayer;
        /**
         * Считает колличество заполненных ячеек
         */
        private int filled;
        /**
         * Всего ячеек
         */
        private int squareCount;
        /**
         * "Судьи" =). После каждого хода они будут проверять,
         * нет ли победителя
         */
        private IWinnerChecker[] winnerCheckers;

        /**
         * Конструктор
         *
         */
        public Game() {
            field = new Square[3][3];
            squareCount = 0;
            // заполнение поля
            for (int i = 0, l = field.length; i < l; i++) {
                for (int j = 0, l2 = field[i].length; j < l2; j++) {
                    field[i][j] = new Square();
                    squareCount++;
                }
            }
            // Инициализация "судей"
            winnerCheckers = new IWinnerChecker[4];
            winnerCheckers[0] = new WinnerCheckerHorizontal(this);
            winnerCheckers[1] = new WinnerCheckerVertical(this);
            winnerCheckers[2] = new WinnerCheckerDiagonalLeft(this);
            winnerCheckers[3] = new WinnerCheckerDiagonalRight(this);
            players = new Player[2];
            started = false;
            activePlayer = null;
            filled = 0;
        }

        public void start() {
            resetPlayers();
            started = true;
        }

        private void resetPlayers() {
            players[0] = new Player("playerX").withMark("X");
            players[1] = new Player("playerO").withMark("O");
            setCurrentActivePlayer(players[0]);
        }

        public Square[][] getField() {
            return field;
        }

        public Player checkWinner() {
            for (IWinnerChecker winChecker : winnerCheckers) {
                Player winner = winChecker.checkWinner();
                if (winner != null) {
                    return winner;
                }
            }
            return null;
        }

        private void setCurrentActivePlayer(Player player) {
            activePlayer = player;
        }

        public boolean makeTurn(int x, int y) {
            if (field[x][y].isFilled()) {
                return false;
            }
            field[x][y].fill(getCurrentActivePlayer());
            filled++;
            switchPlayers();
            return true;
        }

        private void switchPlayers() {
            activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
        }

        public Player getCurrentActivePlayer() {
            return activePlayer;
        }

        public boolean isFieldFilled() {
            return squareCount == filled;
        }

        public void reset() {
            resetField();
            resetPlayers();
        }

        private void resetField() {
            for (int i = 0, l = field.length; i < l; i++) {
                for (int j = 0, l2 = field[i].length; j < l2; j++) {
                    field[i][j].fill(null);
                }
            }
            filled = 0;
        }
    }
