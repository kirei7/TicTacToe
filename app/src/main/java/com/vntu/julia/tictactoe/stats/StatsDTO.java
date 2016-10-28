package com.vntu.julia.tictactoe.stats;


import java.io.Serializable;

public class StatsDTO implements Serializable {
    private String name;
    private long date;

    public StatsDTO(String name, long date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
