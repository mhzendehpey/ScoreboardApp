package com.mxz.board.entities;

import androidx.annotation.NonNull;

public class Game {
    public String ID;
    public String Name;

    public Game() {
    }

    @Override
    @NonNull
    public String toString() {
        return Name;
    }
}
