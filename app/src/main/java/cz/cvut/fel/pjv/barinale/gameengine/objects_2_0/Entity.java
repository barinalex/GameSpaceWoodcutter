package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public abstract class Entity{

    private Rect body;
    private Rect activeZone;
    private Bitmap mainImage = null;
    private int mainImageId;
    private Size size;
    private ArrayList<Item> inventory;
    private Point mapCoordinates;
    private Point screenCoordinates;
    private Characteristic health;
    private Characteristic speed;
    private Characteristic strength;
    private Characteristic protection;

    private Rect fullHealthIndicator;
    private Rect currentHealthIndicator;
    private long startShowHealthTime;

    /**
     *
     * @param mapCoordinates
     */
    public Entity(Point mapCoordinates){
        this.mapCoordinates = mapCoordinates;
        screenCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        setInventory(new ArrayList<Item>());
        body = new Rect();
        activeZone = new Rect();
        setHealth(new Characteristic(0));
        setSpeed(new Characteristic(0));
        setProtection(new Characteristic(0));
        setStrength(new Characteristic(0));
        initializeHealthIndicator();
        startShowHealthTime = -1000;
    }

    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getMainImage(),
                getScreenCoordinates().x - getMainImage().getWidth()/2,
                getScreenCoordinates().y - getMainImage().getHeight()/2, null);
    }

    /**
     *
     * @return
     */
    public boolean timeToDrawHealth(){
        return System.currentTimeMillis() - startShowHealthTime < 1000;
    }

    /**
     *
     * @param canvas
     */
    public void drawHealth(Canvas canvas){
        if (timeToDrawHealth()) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(fullHealthIndicator, paint);
            paint.setColor(Color.RED);
            canvas.drawRect(currentHealthIndicator, paint);
        }
    }

    /**
     *
     * @param userPoint
     * @param mapPosition
     */
    public void update(Point userPoint, Point mapPosition){
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
    }

    /**
     *
     * @return
     */
    public boolean isDead(){
        return health.getCurrent() <= 0;
    }

    /**
     *
     * @param item
     */
    public void addItemEffects(Item item){
        health.changeInitial(item.getHealth().getInitial());
        health.changeCurrent(item.getHealth().getCurrent());
        setCurrentHealthIndicator();
        speed.changeInitial(item.getSpeed().getInitial());
        speed.changeCurrent(item.getSpeed().getCurrent());
        protection.changeInitial(item.getProtection().getInitial());
        protection.changeCurrent(item.getProtection().getCurrent());
        strength.changeInitial(item.getStrength().getInitial());
        strength.changeCurrent(item.getStrength().getCurrent());
    }

    /**
     *
     * @param item
     */
    public void removeItemEffects(Item item){
        health.changeCurrent(-item.getHealth().getCurrent());
        health.changeInitial(-item.getHealth().getInitial());
        speed.changeCurrent(-item.getSpeed().getCurrent());
        speed.changeInitial(-item.getSpeed().getInitial());
        protection.changeCurrent(-item.getProtection().getCurrent());
        protection.changeInitial(-item.getProtection().getInitial());
        strength.changeCurrent(-item.getStrength().getCurrent());
        strength.changeInitial(-item.getStrength().getInitial());
    }

    /**
     *
     */
    public void initializeHealthIndicator(){
        fullHealthIndicator = new Rect(GamePanel.SCREEN_WIDTH - 120, 20, GamePanel.SCREEN_WIDTH - 20, 40);
        currentHealthIndicator = new Rect(GamePanel.SCREEN_WIDTH - 120, 20, GamePanel.SCREEN_WIDTH - 20, 40);
    }

    /**
     *
     */
    public void setCurrentHealthIndicator(){
        currentHealthIndicator.set(GamePanel.SCREEN_WIDTH - 120, currentHealthIndicator.top, GamePanel.SCREEN_WIDTH - 20 - getCurrentHealthDecrement(), currentHealthIndicator.bottom);
    }

    /**
     *
     * @return
     */
    public int getCurrentHealthDecrement(){
        if (health.getInitial() != 0)
            return (health.getInitial() - health.getCurrent()) * 100 / health.getInitial();
        return 0;
    }

    /**
     *
     * @param damage
     */
    public void getDamage(int damage){
        damage = (getProtection().getCurrent() > damage) ? 0: getProtection().getCurrent() - damage;
        getHealth().changeCurrent(damage);
        setCurrentHealthIndicator();
        startShowHealthTime = System.currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public int getMainImageId() {
        return mainImageId;
    }

    /**
     *
     * @param mainImageId
     */
    public void setMainImageId(int mainImageId) {
        this.mainImageId = mainImageId;
    }

    /**
     *
     * @return
     */
    public Rect getBody() {
        return body;
    }

    /**
     *
     * @param body
     */
    public void setBody(Rect body) {
        this.body = body;
    }

    /**
     *
     */
    public void setBody(){
        int width, heigth;
        if (getMainImage() == null){
            width = getSize().getWidth();
            heigth = getSize().getHeight();
        }
        else {
            width = getMainImage().getWidth();
            heigth = getMainImage().getHeight();
        }
        getBody().set(getMapCoordinates().x - width / 2,
                getMapCoordinates().y - heigth / 4,
                getMapCoordinates().x + width / 2,
                getMapCoordinates().y + heigth / 2);
    }

    /**
     *
     * @return
     */
    public Rect getActiveZone() {
        return activeZone;
    }

    /**
     *
     * @param activeZone
     */
    public void setActiveZone(Rect activeZone) {
        this.activeZone = activeZone;
    }

    /**
     *
     */
    public void setActiveZone(){
        int width, heigth;
        if (getMainImage() == null){
            width = getSize().getWidth();
            heigth = getSize().getHeight();
        }
        else {
            width = getMainImage().getWidth();
            heigth = getMainImage().getHeight();
        }
        getActiveZone().set(getMapCoordinates().x - width*5/8,
                getMapCoordinates().y - heigth*5/8,
                getMapCoordinates().x + width*5/8,
                getMapCoordinates().y + heigth*5/8);
    }

    /**
     *
     * @return
     */
    public Bitmap getMainImage() {
        return mainImage;
    }

    /**
     *
     * @param mainImage
     */
    public void setMainImage(Bitmap mainImage) {
        this.mainImage = mainImage;
    }

    /**
     *
     * @return
     */
    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    /**
     *
     * @param mapCoordinates
     */
    public void setMapCoordinates(Point mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    /**
     *
     * @return
     */
    public Point getScreenCoordinates() {
        return screenCoordinates;
    }

    /**
     *
     * @param screenCoordinates
     */
    public void setScreenCoordinates(Point screenCoordinates) {
        this.screenCoordinates = screenCoordinates;
    }

    /**
     *
     * @return
     */
    public Characteristic getHealth() {
        return health;
    }

    /**
     *
     * @param health
     */
    public void setHealth(Characteristic health) {
        this.health = health;
    }

    /**
     *
     * @return
     */
    public Characteristic getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(Characteristic speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     */
    public Characteristic getStrength() {
        return strength;
    }

    /**
     *
     * @param strength
     */
    public void setStrength(Characteristic strength) {
        this.strength = strength;
    }

    /**
     *
     * @return
     */
    public Characteristic getProtection() {
        return protection;
    }

    /**
     *
     * @param protection
     */
    public void setProtection(Characteristic protection) {
        this.protection = protection;
    }

    /**
     *
     * @return
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     *
     * @param inventory
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     *
     * @return
     */
    public Rect getFullHealthIndicator() {
        return fullHealthIndicator;
    }

    /**
     *
     * @param fullHealthIndicator
     */
    public void setFullHealthIndicator(Rect fullHealthIndicator) {
        this.fullHealthIndicator = fullHealthIndicator;
    }

    /**
     *
     * @return
     */
    public Rect getCurrentHealthIndicator() {
        return currentHealthIndicator;
    }

    /**
     *
     * @param currentHealthIndicator
     */
    public void setCurrentHealthIndicator(Rect currentHealthIndicator) {
        this.currentHealthIndicator = currentHealthIndicator;
    }

    /**
     *
     * @return
     */
    public long getStartShowHealthTime() {
        return startShowHealthTime;
    }

    /**
     *
     * @param startShowHealthTime
     */
    public void setStartShowHealthTime(long startShowHealthTime) {
        this.startShowHealthTime = startShowHealthTime;
    }

    /**
     *
     * @return
     */
    public Size getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
    }
}
