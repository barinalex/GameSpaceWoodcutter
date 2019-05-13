package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class MissionScroll extends Scroll{
    /**
     *
     * @param mapCoordinates
     */
    public MissionScroll(Point mapCoordinates) {
        super(mapCoordinates);
        initializeScroll(R.drawable.scroll, R.drawable.mission_scroll);
    }
}
