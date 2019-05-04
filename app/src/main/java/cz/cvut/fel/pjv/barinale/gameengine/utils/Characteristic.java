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

    public void changeCurrent(int changer){
        this.current += changer;
        //current = (current > initial)? initial: current;
        //current = (current < 0)? 0: current;
    }
}
