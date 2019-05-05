package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class DeadBody extends Potion {
    public DeadBody(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.one_eye_dead);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(3));
    }
}
