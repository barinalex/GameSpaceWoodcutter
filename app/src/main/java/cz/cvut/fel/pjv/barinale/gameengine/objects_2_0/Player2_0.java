package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Player2_0 extends Creature{

    public Player2_0(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.redhead);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(10,10));
        setSpeed(new Characteristic(15,15));
        setProtection(new Characteristic(2, 2));
        setStrength(new Characteristic(3, 3));
        setAttackDelay(500);
    }

    public void action(Point userPoint){
        for (Entity entity: EntityManager.entities){
            if (checkTarget(userPoint, entity)) {
                if (entity instanceof Item) {
                    pickUp((Item) entity);
                } else {
                    attack(entity);
                }
            }
        }
    }

    private void pickUp(Item item){
        getInventory().add(item);
        EntityManager.entities.remove(item);
    }

    public void discard(int index){
        Item item = getInventory().get(index);
        item.setMapCoordinates(new Point(getMapCoordinates().x + item.getMainImage().getWidth(),
                getMapCoordinates().y + item.getMainImage().getHeight()));
        EntityManager.entities.add(0, item);
        getInventory().remove(item);
    }

    private boolean checkTarget(Point userPoint, Entity entity){
        return entity != this &&
                entity.getBody().contains(userPoint.x, userPoint.y) &&
                Rect.intersects(getActiveZone(), entity.getActiveZone());
    }
}
