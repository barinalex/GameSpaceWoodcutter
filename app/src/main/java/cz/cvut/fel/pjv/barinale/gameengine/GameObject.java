package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Canvas;

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
