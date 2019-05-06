package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.CherryWood;

public class CherryTree extends Tree{
    public CherryTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.cherry, 20, 3);
        getInventory().add(new CherryWood(new Point()));
    }
}
