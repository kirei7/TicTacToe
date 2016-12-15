package com.vntu.julia.tictactoe.game;

//абстракція квадрату поля, потрібна лише для того щоб перевіряти
//чи був у цьому квадраті хід
public class Square {
    private Player player = null;

    public void fill(Player player) {
        this.player = player;
    }

    public boolean isFilled() {
        if (player != null) {
            return true;
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }
}