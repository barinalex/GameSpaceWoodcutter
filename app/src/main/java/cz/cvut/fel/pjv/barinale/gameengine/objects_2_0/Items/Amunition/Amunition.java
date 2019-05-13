package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;

public abstract class Amunition extends Item {
    /**
     *
     * @param mapCoordinates
     */
    public Amunition(Point mapCoordinates) {
        super(mapCoordinates);
    }
}
