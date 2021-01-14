package SnapShot;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Originator {
    private long state;
    private Color colors;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public Color getColors() {
        return colors;
    }

    public void setColors(Color colors) {
        this.colors = colors;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void  getStateFromMemento(Memento memento){
        state = memento.getState();
    }

    public Memento saveColorsToMemento(){
        return new Memento(colors);
    }

    public void  getColorsFromMemento(Memento memento){
        colors = memento.getColors();
    }


}
