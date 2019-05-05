package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.DeadOneEye;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class OneEye extends Enemy{
    public OneEye(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.one_eye1);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        getMoveImages().add(getMainImage());
        setBody();
        setActiveZone();
        setHealth(new Characteristic(7));
        setSpeed(new Characteristic(10));
        setProtection(new Characteristic(0));
        setStrength(new Characteristic(3, 3));
        setAttackDelay(750);
        getInventory().add(new DeadOneEye(new Point()));
    }
}
