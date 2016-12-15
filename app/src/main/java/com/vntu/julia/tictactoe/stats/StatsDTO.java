package com.vntu.julia.tictactoe.stats;


import java.io.Serializable;

/*клас, що використовується як абстракція для передачі даних у бд і отримання їх з бд
* це типу шоб не просити в бд окремо імена і дати, ми просимо в неї "пакунок" з іменем і датою
* весь клас складається власне з полів даних і методів для їх отримання та задання*/
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
