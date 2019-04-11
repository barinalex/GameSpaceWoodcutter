package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Canvas;
import android.graphics.Point;

public interface ObjectInterface {
    public void draw(Canvas canvas);
    public void update(Point point, Point mapPosition);
}
