package cz.cvut.fel.pjv.barinale.gameengine.utils;

public class Characteristic {
    private int initial;
    private int current;
    public Characteristic(int initial, int current){
        this.initial = initial;
        this.current = current;
    }

    public Characteristic(int initial){
        this.initial = initial;
        this.current = initial;
    }

    public int getInitial() {
        return initial;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void changeInitial(int changer){
        initial += changer;
        current = (current > initial)? initial: current;
    }

    public void changeCurrent(int changer){
        current += changer;
        current = (current < 0) ? 0: current;
    }

    public void restore(int value){
        current += value;
        current = (current > initial)? initial: current;
    }
}
