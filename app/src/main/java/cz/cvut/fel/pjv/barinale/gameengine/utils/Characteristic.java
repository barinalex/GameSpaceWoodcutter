package cz.cvut.fel.pjv.barinale.gameengine.utils;

public class Characteristic {
    private int initial;
    private int current;

    /**
     *
     * @param initial
     * @param current
     */
    public Characteristic(int initial, int current){
        this.initial = initial;
        this.current = current;
    }

    /**
     *
     * @param initial
     */
    public Characteristic(int initial){
        this.initial = initial;
        this.current = initial;
    }

    /**
     *
     * @return
     */
    public int getInitial() {
        return initial;
    }

    /**
     *
     * @return
     */
    public int getCurrent() {
        return current;
    }

    /**
     *
     * @param current
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     *
     * @param changer
     */
    public void changeInitial(int changer){
        initial += changer;
        current = (current > initial)? initial: current;
    }

    /**
     *
     * @param changer
     */
    public void changeCurrent(int changer){
        current += changer;
        current = (current < 0) ? 0: current;
    }

    /**
     *
     * @param value
     */
    public void restore(int value){
        current += value;
        current = (current > initial)? initial: current;
    }
}
