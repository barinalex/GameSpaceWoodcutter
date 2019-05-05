package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.GreenWood;

public class GreenTree extends Tree{
    public GreenTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.green, 10, 1);
        getInventory().add(new GreenWood(new Point()));
    }
}
