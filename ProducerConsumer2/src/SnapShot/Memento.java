package SnapShot;

public class Memento {
    private long state;

    public Memento(long state) {
        this.state = state;
    }

    public long getState() {
        return state;
    }
}
