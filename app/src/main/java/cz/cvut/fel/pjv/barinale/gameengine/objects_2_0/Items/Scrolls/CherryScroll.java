package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class CherryScroll extends Scroll{
    /**
     *
     * @param mapCoordinates
     */
    public CherryScroll(Point mapCoordinates) {
        super(mapCoordinates);
        initializeScroll(R.drawable.scroll, R.drawable.cherry_scroll);
    }
}
