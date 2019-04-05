package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;

public class Vector {
    public static Point substract_vector(Point a, Point b){
        return new Point(a.x - b.x, a.y - b.y);
    }
}
