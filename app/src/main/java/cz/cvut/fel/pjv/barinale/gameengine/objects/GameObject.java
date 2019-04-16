package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public class GameObject implements ObjectInterface {
    private Rect body;
    private Rect activeZone;
    private ArrayList<GameObject> inventory;
    private ArrayList<Bitmap> images;
    private Point mapCoordinates;
    private Point screenCoordinates;
    private int[]characteristics;
    private int type;
    private int imageWidth, imageHeight;
    private String name;

    public Rect getBody() {
        return body;
    }

    public Rect getActiveZone() {
        return activeZone;
    }

    public ArrayList<GameObject> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<GameObject> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    public void setMapCoordinates(Point mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    public Point getScreenCoordinates() {
        return screenCoordinates;
    }

    public void setScreenCoordinates(Point screenCoordinates) {
        this.screenCoordinates = screenCoordinates;
    }

    public int[] getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(int[] characteristics) {
        this.characteristics = characteristics;
    }

    public void decreaseHealth(int decrement) {
        characteristics[Constants.HEALTH] -= decrement;
    }

    public int getType() {
        return type;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameObject(ArrayList<Bitmap> images, ArrayList<GameObject> inventory,
                      Point mapCoordinates, int[]characteristics , String name, int type){
        this.images = images;
        this.inventory = inventory;
        this.mapCoordinates = mapCoordinates;
        this.characteristics = characteristics;
        this.name = name;
        this.type = type;
        //body = new Rect();
        body = (type != Constants.ITEM) ? new Rect(): null;
        activeZone = new Rect();
        screenCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        imageWidth = images.get(Constants.MAINIMAGE).getWidth();
        imageHeight = images.get(Constants.MAINIMAGE).getHeight();
        setZones();
    }

    public void setZones(){
        if(body != null) {
            body.set(mapCoordinates.x - imageWidth / 4,
                    mapCoordinates.y - imageHeight / 4,
                    mapCoordinates.x + imageWidth / 4,
                    mapCoordinates.y + imageHeight * 3 / 8);
        }
        activeZone.set(mapCoordinates.x - imageWidth*5/8,
                mapCoordinates.y - imageHeight*5/8,
                mapCoordinates.x + imageWidth*5/8,
                mapCoordinates.y + imageHeight*5/8);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(images.get(0), screenCoordinates.x - imageWidth/2,
                screenCoordinates.y - imageHeight/2, null);
    }

    @Override
    public void update(Point point, Point mapPosition) {
        if (characteristics[Constants.SPEED] != 0) {
            Point direction = Utils.get_direction(point, mapCoordinates,
                    characteristics[Constants.SPEED]);
            Point old_coordinates = new Point(mapCoordinates.x, mapCoordinates.y);
            mapCoordinates.x += direction.x;
            mapCoordinates.y += direction.y;
            setZones();
            if (CollisionDetecter.chechCollision(this)) {
                mapCoordinates.set(old_coordinates.x, old_coordinates.y);
                setZones();
            }
        }
        else {
            setZones();
        }
        screenCoordinates.set(mapCoordinates.x + mapPosition.x,
                mapCoordinates.y + mapPosition.y);
    }
}
