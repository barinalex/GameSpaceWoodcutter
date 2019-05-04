package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Player extends GameObject{

    public Player(ArrayList<Bitmap> images, ArrayList<GameObject> inventory, Point mapCoordinates, int[] characteristics, int[] initialCharacteristics, String name, int type) {
        super(images, inventory, mapCoordinates, characteristics, initialCharacteristics, name, type);
        setFullHealth(new Rect(20, 20, 120, 40));
        setHealthIndicator(new Rect(20, 20, 120, 40));
        setHealthIndicator();
    }

    @Override
    public void initializeHealthIndicator() {
        setFullHealth(new Rect(20, 20, 120, 40));
        setHealthIndicator(new Rect(20, 20, 120, 40));
    }

    @Override
    public void setHealthIndicator() {
        getHealthIndicator().set(20, getHealthIndicator().top, 120 - getHealthIndicatorDecrement(), getHealthIndicator().bottom);
    }

    public boolean pick_up_to_inventory(ArrayList<GameObject> map_objects, Point userPoint){
        GameObject gameObject;
        for (int i = 0; i < map_objects.size(); i++) {
            gameObject = map_objects.get(i);
            if (gameObject.getType() == Constants.ITEM &&
                Rect.intersects(getBody(), gameObject.getActiveZone())
                && gameObject.getActiveZone().contains(userPoint.x, userPoint.y)) {
                getInventory().add(gameObject);
                map_objects.remove(map_objects.remove(i--));
                return true;
            }
        }
        return false;
    }

    public int getAttack(){
        int attack = getCharacteristics()[Constants.STRENGHT];
        for (GameObject item: getInventory()){
            attack += item.getCharacteristics()[Constants.STRENGHT];
        }
        return attack;
    }
}
