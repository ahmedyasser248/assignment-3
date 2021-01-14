package SnapShot;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Memento {
    private long state;
    private Color colors;

    public Memento(long state) {
        this.state = state;
    }

    public Memento(Color colors){
        this.colors = colors;

    }
    public long getState() {
        return state;
    }

    public Color getColors(){
        return this.colors;
    }
}
