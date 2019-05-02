package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;

public abstract class Entity{
    private Rect body;
    private Rect activeZone;
    Bitmap mainImage;
    int mainImageId;
    private ArrayList<Item> inventory;
    private Point mapCoordinates;
    private Point screenCoordinates;
    private Characteristic health;
    private Characteristic speed;
    private Characteristic strength;
    private Characteristic protection;
    int type;

    public Entity(Point mapCoordinates){
        this.mapCoordinates = mapCoordinates;
        screenCoordinates = new Point();
        setInventory(new ArrayList<Item>());
        body = new Rect();
        activeZone = new Rect();
    }

    public abstract void draw(Canvas canvas);

    public abstract void update(Point userPoint, Point mapPosition);

    public void getDamage(int damage){
        damage = (getProtection().getCurrent() > damage) ? 0: getProtection().getCurrent() - damage;
        getHealth().changeCurrent(damage);
    }

    public int getMainImageId() {
        return mainImageId;
    }

    public void setMainImageId(int mainImageId) {
        this.mainImageId = mainImageId;
    }

    public Rect getBody() {
        return body;
    }

    public void setBody(Rect body) {
        this.body = body;
    }

    public void setBody(){
        getBody().set(getMapCoordinates().x - getMainImage().getWidth() / 2,
                getMapCoordinates().y - getMainImage().getHeight() / 4,
                getMapCoordinates().x + getMainImage().getWidth() / 2,
                getMapCoordinates().y + getMainImage().getHeight() / 2);
    }

    public Rect getActiveZone() {
        return activeZone;
    }

    public void setActiveZone(Rect activeZone) {
        this.activeZone = activeZone;
    }

    public void setActiveZone(){
        getActiveZone().set(getMapCoordinates().x - getMainImage().getWidth()*5/8,
                getMapCoordinates().y - getMainImage().getHeight()*5/8,
                getMapCoordinates().x + getMainImage().getWidth()*5/8,
                getMapCoordinates().y + getMainImage().getHeight()*5/8);
    }

    public Bitmap getMainImage() {
        return mainImage;
    }

    public void setMainImage(Bitmap mainImage) {
        this.mainImage = mainImage;
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

    public Characteristic getHealth() {
        return health;
    }

    public void setHealth(Characteristic health) {
        this.health = health;
    }

    public Characteristic getSpeed() {
        return speed;
    }

    public void setSpeed(Characteristic speed) {
        this.speed = speed;
    }

    public Characteristic getStrength() {
        return strength;
    }

    public void setStrength(Characteristic strength) {
        this.strength = strength;
    }

    public Characteristic getProtection() {
        return protection;
    }

    public void setProtection(Characteristic protection) {
        this.protection = protection;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
}
