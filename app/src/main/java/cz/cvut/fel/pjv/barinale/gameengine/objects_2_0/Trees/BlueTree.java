package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Wood.BlueWood;

public class BlueTree extends Tree{
    public BlueTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.blue, 15, 2);
        getInventory().add(new BlueWood(new Point()));
    }
}
