package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.OrangeWood;

public class OrangeTree extends Tree{
    public OrangeTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.orange, 10, 2);
        getInventory().add(new OrangeWood(new Point()));
    }
}
