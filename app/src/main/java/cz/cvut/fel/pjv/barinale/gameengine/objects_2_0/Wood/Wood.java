package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Wood;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public abstract class Wood extends Item {
    public Wood(Point mapCoordinates) {
        super(mapCoordinates);
        setSpeed(new Characteristic(-1));
    }

    public void initializeWood(int imageId){
        setMainImageId(imageId);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
    }
}
