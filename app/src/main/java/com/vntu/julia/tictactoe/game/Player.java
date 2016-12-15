package com.vntu.julia.tictactoe.game;


public class Player {
    //ім'я гравця
    private String name;
    //марка, якою помічається поле при ході гравця
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