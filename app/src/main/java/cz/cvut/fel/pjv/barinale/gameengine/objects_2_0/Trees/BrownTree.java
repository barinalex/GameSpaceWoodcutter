package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BrownWood;

public class BrownTree extends Tree{
    public BrownTree(Point mapCoordinates) {
        super(mapCoordinates);
        initializeTree(R.drawable.brown, 20, 1);
        getInventory().add(new BrownWood(new Point()));
    }
}
