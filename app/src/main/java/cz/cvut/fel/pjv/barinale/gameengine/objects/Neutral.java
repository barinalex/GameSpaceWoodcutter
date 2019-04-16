package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

public class Neutral extends GameObject{
    public Neutral(ArrayList<Bitmap> images, ArrayList<GameObject> inventory, Point mapCoordinates, int[] characteristics, String name, int type) {
        super(images, inventory, mapCoordinates, characteristics, name, type);
    }
}
