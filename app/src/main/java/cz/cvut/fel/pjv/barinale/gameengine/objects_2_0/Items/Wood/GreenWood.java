package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class GreenWood extends Wood{
    public GreenWood(Point mapCoordinates) {
        super(mapCoordinates);
        initializeWood(R.drawable.greenwood);
    }
}
