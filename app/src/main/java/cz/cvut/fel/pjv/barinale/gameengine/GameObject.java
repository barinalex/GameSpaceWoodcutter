package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class GameObject implements ObjectInterface{
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
    private boolean destroid = false;

    public ArrayList<GameObject> getInventory() {
        return inventory;
    }

    public int getType() {
        return type;
    }

    public Rect getActiveZone() {
        return activeZone;
    }

    public int[] getCharacteristics() {
        return characteristics;
    }

    public Rect getBody() {
        return body;
    }

    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setMapCoordinates(Point mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    public void setScreenCoordinates(Point screenCoordinates) {
        this.screenCoordinates = screenCoordinates;
    }



    public GameObject(ArrayList<Bitmap> images, ArrayList<GameObject> inventory, Point mapCoordinates,
                      int[]characteristics , String name, int type) {
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
        set_body();
    }

    private void set_body(){
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
    public void update(Point userPoint) {

    }

    public void update(Point point, Point mapPosition) {
        if (characteristics[Constants.SPEED] != 0) {
            Point direction = Vector.get_direction(point, mapCoordinates, characteristics[Constants.SPEED]);
            Point old_coordinates = new Point(mapCoordinates.x, mapCoordinates.y);
            mapCoordinates.x += direction.x;
            mapCoordinates.y += direction.y;
            set_body();
            if (CollisionDetecter.chech_collision(this)) {
                mapCoordinates.set(old_coordinates.x, old_coordinates.y);
                set_body();
            }
        }
        screenCoordinates.set(mapCoordinates.x + mapPosition.x, mapCoordinates.y + mapPosition.y);
    }
}
