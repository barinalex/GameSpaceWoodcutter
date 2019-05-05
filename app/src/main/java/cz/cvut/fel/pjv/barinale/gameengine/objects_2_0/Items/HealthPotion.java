package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class HealthPotion extends Potion {
    public HealthPotion(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.health_potion);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(10,10));
    }
}
