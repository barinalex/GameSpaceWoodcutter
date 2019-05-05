package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.DeadBody;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Enemy extends Creature {

    public Enemy(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.one_eye1);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(8,8));
        setSpeed(new Characteristic(10,10));
        setProtection(new Characteristic(1, 1));
        setStrength(new Characteristic(3, 3));
        setAttackDelay(750);
        getInventory().add(new DeadBody(new Point()));
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
        if (nearThePlayer()){
            attack(EntityManager.player);
        }
    }
}
