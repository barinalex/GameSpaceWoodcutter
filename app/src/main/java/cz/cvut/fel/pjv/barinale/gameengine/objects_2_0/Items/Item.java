package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;

public abstract class Item extends Entity {
    /**
     *
     * @param mapCoordinates
     */
    public Item(Point mapCoordinates) {
        super(mapCoordinates);
    }
}
