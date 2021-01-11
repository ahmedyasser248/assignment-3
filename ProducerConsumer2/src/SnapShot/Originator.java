package SnapShot;

public class Originator {
    private long state;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void  getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
