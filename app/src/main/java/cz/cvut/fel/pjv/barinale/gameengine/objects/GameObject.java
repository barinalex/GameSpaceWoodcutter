package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public class GameObject implements ObjectInterface {
    private Rect body;
    private Rect activeZone;

    private Rect fullHealth;
    private Rect healthIndicator;

    private ArrayList<GameObject> inventory;
    private ArrayList<Bitmap> images;
    private Point mapCoordinates;
    private Point screenCoordinates;
    private int[] initialCharacteristics;
    private int[]characteristics;
    private int type;
    private int imageWidth, imageHeight;
    private String name;

    public Rect getFullHealth() {
        return fullHealth;
    }

    public void setFullHealth(Rect fullHealth) {
        this.fullHealth = fullHealth;
    }

    public Rect getHealthIndicator() {
        return healthIndicator;
    }

    public void setHealthIndicator(Rect healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

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

    public int[] getInitialCharacteristics() {
        return initialCharacteristics;
    }

    public GameObject(ArrayList<Bitmap> images, ArrayList<GameObject> inventory,
                      Point mapCoordinates, int[]characteristics,
                      int[]initialCharacteristics, String name, int type){
        this.images = images;
        this.inventory = inventory;
        this.mapCoordinates = mapCoordinates;
        this.characteristics = characteristics;
        this.initialCharacteristics = initialCharacteristics;
        this.name = name;
        this.type = type;
        body = (type != Constants.ITEM) ? new Rect(): null;
        activeZone = new Rect();
        screenCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        imageWidth = images.get(Constants.MAINIMAGE).getWidth();
        imageHeight = images.get(Constants.MAINIMAGE).getHeight();
        setZones();
        initializeHealthIndicator();
        setHealthIndicator();
    }

    public void initializeHealthIndicator(){
        fullHealth = new Rect(Constants.SCREEN_WIDTH - 120, 20, Constants.SCREEN_WIDTH - 20, 40);
        healthIndicator = new Rect(Constants.SCREEN_WIDTH - 120, 20, Constants.SCREEN_WIDTH - 20, 40);
    }

    public void setHealthIndicator(){
        healthIndicator.set(healthIndicator.left, healthIndicator.top, Constants.SCREEN_WIDTH - 20 - getHealthIndicatorDecrement(), healthIndicator.bottom);
    }

    public int getHealthIndicatorDecrement(){
        if (initialCharacteristics[Constants.HEALTH] != 0)
            return (initialCharacteristics[Constants.HEALTH] - characteristics[Constants.HEALTH]) * 100 / initialCharacteristics[Constants.HEALTH];
        return 0;
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

    public void drawHealth(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(fullHealth, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(healthIndicator, paint);
    }

    @Override
    public void update(Point point, Point mapPosition) {
        setHealthIndicator();
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
