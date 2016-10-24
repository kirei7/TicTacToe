package com.vntu.julia.tictactoe.game;


public class Player {
    private String name;
    private String mark;

    public Player(String name) {
        this.name = name;
    }
    public Player withMark(String mark) {
        this.mark = mark;
        return this;
    }

    public String getName() {
        return  name;
    }
    public String getMark() {
        return mark;
    }
}