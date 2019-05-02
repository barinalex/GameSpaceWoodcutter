package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Enemy2_0 extends Creature {

    public Enemy2_0(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.protagonist);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(8,8));
        setSpeed(new Characteristic(10,10));
        setProtection(new Characteristic(1, 1));
        setStrength(new Characteristic(3, 3));
        setAttackDelay(750);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
        if (nearThePlayer()){
            attack(EntityManager.player);
        }
        if (getHealth().getCurrent() == 0){
            EntityManager.entities.remove(this);
        }
    }

    private boolean nearThePlayer(){
        return Rect.intersects(getActiveZone(), EntityManager.player.getActiveZone());
    }
}
