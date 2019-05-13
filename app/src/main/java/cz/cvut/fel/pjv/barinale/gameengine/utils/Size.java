package cz.cvut.fel.pjv.barinale.gameengine.utils;

public class Size {
    private int width;
    private int height;

    /**
     *
     */
    public Size() {
    }

    /**
     *
     * @param width
     * @param height
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
