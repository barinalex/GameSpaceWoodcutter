package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Runes;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class RedRune extends Rune{
    public RedRune(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.red_rune);
        setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        setBody();
        setActiveZone();
    }
}
