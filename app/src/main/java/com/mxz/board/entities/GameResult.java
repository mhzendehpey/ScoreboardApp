package com.mxz.board.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class GameResult {
    public String ID;
    public String Date;
    public int Year;
    public int Month;
    public int Day;
    public Game Game;
    public List<Rank> Rankings;

    public GameResult() {
    }

    public String getDateString() {
        String[] date = Date.split("/");
        return date[2] + date[1] + date[0];
    }
}
