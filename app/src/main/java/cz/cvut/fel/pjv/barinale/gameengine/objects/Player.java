package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Player extends GameObject{

    public Player(ArrayList<Bitmap> images, ArrayList<GameObject> inventory,
                  Point mapCoordinates, int[] characteristics, String name, int type) {
        super(images, inventory, mapCoordinates, characteristics, name, type);
    }

    public void pick_up_to_inventory(ArrayList<GameObject> map_objects){
        GameObject gameObject;
        for (int i = 0; i < map_objects.size(); i++) {
            gameObject = map_objects.get(i);
            if (gameObject.getType() == Constants.ITEM &&
                    Rect.intersects(getBody(), gameObject.getActiveZone())) {
                getInventory().add(gameObject);
                getCharacteristics()[Constants.STRENGHT] +=
                        gameObject.getCharacteristics()[Constants.STRENGHT];
                map_objects.remove(map_objects.remove(i--));
            }
        }
    }
}
