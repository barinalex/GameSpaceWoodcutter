package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class CherryWood extends Wood{
    /**
     *
     * @param mapCoordinates
     */
    public CherryWood(Point mapCoordinates) {
        super(mapCoordinates);
        initializeWood(R.drawable.cherrywood);
    }
}
