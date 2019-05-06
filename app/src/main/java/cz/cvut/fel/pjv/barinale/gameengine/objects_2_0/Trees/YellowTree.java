package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.YellowWood;

public class YellowTree extends Tree{
    public YellowTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.yellow, 20, 2);
        getInventory().add(new YellowWood(new Point()));
    }
}
