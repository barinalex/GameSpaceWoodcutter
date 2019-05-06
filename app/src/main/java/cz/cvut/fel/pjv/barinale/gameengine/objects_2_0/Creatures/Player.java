package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.Amunition;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.Corpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.Wood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Teleport;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Player extends Creature{

    public Player(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.redhead);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setMoveImages(new ArrayList<Bitmap>());
        getMoveImages().add(getMainImage());
        getMoveImages().add(BitmapFactory.decodeResource(Constants.resources, R.drawable.redhead_1));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(25));
        setSpeed(new Characteristic(15));
        setProtection(new Characteristic(2));
        setStrength(new Characteristic(2));
        setAttackDelay(500);
    }

    public boolean action(Point userPoint){
        boolean acted = false;
        for (Entity entity: EntityManager.entities){
            if (checkTarget(userPoint, entity)) {
                if (entity instanceof Item) {
                    pickUp((Item) entity);
                    acted = true;
                    break;
                }
                else if(entity instanceof Teleport){
                    ((Teleport) entity).teleportate();
                    ((Teleport) entity).pushWood(getAllWood());
                    acted = true;
                    break;
                }
                else {
                    attack(entity);
                    acted = entity.isDead();
                    break;
                }
            }
        }
        return acted;
    }

    private ArrayList<Wood> getAllWood(){
        ArrayList<Wood> woods = new ArrayList<>();
        for (int i = 0; i < getInventory().size(); i++) {
            Item item = getInventory().get(i);
            if (item instanceof Wood){
                woods.add((Wood) item);
                removeItemEffects(item);
                getInventory().remove(item);
                i--;
            }
        }
        return woods;
    }

    private void pickUp(Item newItem){
        int size = getInventory().size();
        if ((newItem instanceof Amunition)) {
            for (int i = 0; i < size; i++) {
                Item item = getInventory().get(i);
                if ((item.getClass().getSuperclass().getSimpleName().equals(newItem.getClass().getSuperclass().getSimpleName()))) {
                    discardItem(i);
                }
            }
        }
        getInventory().add(newItem);
        EntityManager.entities.remove(newItem);
        if (!(newItem instanceof Corpus)) {
            addItemEffects(newItem);
        }
    }

    public void discardItem(int index){
        Item item = getInventory().get(index);
        removeItemEffects(item);
        item.setMapCoordinates(new Point(getMapCoordinates().x + item.getMainImage().getWidth(),
                getMapCoordinates().y + item.getMainImage().getHeight()));
        item.setBody();
        item.setActiveZone();
        EntityManager.entities.add(1, item);
        getInventory().remove(item);
    }

    private boolean checkTarget(Point userPoint, Entity entity){
        return entity != this &&
                entity.getActiveZone().contains(userPoint.x, userPoint.y) &&
                Rect.intersects(getBody(), entity.getActiveZone());
    }

    @Override
    public void initializeHealthIndicator() {
        setFullHealthIndicator(new Rect(20, 20, 120, 40));
        setCurrentHealthIndicator(new Rect(20, 20, 120, 40));
    }

    @Override
    public void setCurrentHealthIndicator() {
        getCurrentHealthIndicator().set(getCurrentHealthIndicator().left, getCurrentHealthIndicator().top, 120 - getCurrentHealthDecrement(), getCurrentHealthIndicator().bottom);
    }

    @Override
    public boolean timeToDrawHealth() {
        return true;
    }
}
