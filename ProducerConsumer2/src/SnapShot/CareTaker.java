package SnapShot;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    private List<Memento> mementoColorList = new ArrayList<Memento>();


    public void add (Memento state){
        mementoList.add(state);
    }

    public Memento get(int index) {
        return mementoList.get(index);
    }

    public void addColor (Memento colors){
        mementoColorList.add(colors);
    }

    public Memento getColor(int index) {
        return mementoColorList.get(index);
    }
}
