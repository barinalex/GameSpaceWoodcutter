package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Axe extends Item {
    public Axe(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.axe);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setStrength(new Characteristic(2,2));
    }
}
