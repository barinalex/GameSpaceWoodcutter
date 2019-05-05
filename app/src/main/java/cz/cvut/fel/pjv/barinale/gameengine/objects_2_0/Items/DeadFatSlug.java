package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class DeadFatSlug extends Potion{
    public DeadFatSlug(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.fat_slug_dead);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(5));
    }
}
