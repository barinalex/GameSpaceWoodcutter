package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

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

    private Rect fullHealth;
    private Rect currentHealth;
    private long startShowHealthTime;

    public Entity(Point mapCoordinates){
        this.mapCoordinates = mapCoordinates;
        screenCoordinates = new Point();
        setInventory(new ArrayList<Item>());
        body = new Rect();
        activeZone = new Rect();
        initializeHealthIndicator();
        startShowHealthTime = -5000;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(getMainImage(),
                getScreenCoordinates().x - getMainImage().getWidth()/2,
                getScreenCoordinates().y - getMainImage().getHeight()/2, null);
        if (timeToDrawHealth()){
            drawHealth(canvas);
        }
    }

    public boolean timeToDrawHealth(){
        return System.currentTimeMillis() - startShowHealthTime < 1000;
    }

    public void drawHealth(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(fullHealth, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(currentHealth, paint);
    }

    public void update(Point userPoint, Point mapPosition){
        if (isDead()){
            for (Item item: inventory){
                discard(item);
            }
            EntityManager.entities.remove(this);
            return;
        }
    }

    public void discard(Item item){
        item.setMapCoordinates(new Point(getMapCoordinates().x + item.getMainImage().getWidth(),
                getMapCoordinates().y + item.getMainImage().getHeight()));
        EntityManager.entities.add(0, item);
        inventory.remove(item);
    }

    public boolean isDead(){
        return health.getCurrent() <= 0;
    }

    public void addItemsEffects(){
        for (Item item: inventory){
            addItemEffects(item);
        }
    }

    public void addItemEffects(Item item){
        health.changeCurrent(item.getHealth().getCurrent());
        speed.changeCurrent(item.getSpeed().getCurrent());
        protection.changeCurrent(item.getProtection().getCurrent());
        strength.changeCurrent(item.getStrength().getCurrent());
    }

    public void removeItemEffects(Item item){
        health.changeCurrent(-item.getHealth().getCurrent());
        speed.changeCurrent(-item.getSpeed().getCurrent());
        protection.changeCurrent(-item.getProtection().getCurrent());
        strength.changeCurrent(-item.getStrength().getCurrent());
    }

    public void initializeHealthIndicator(){
        fullHealth = new Rect(Constants.SCREEN_WIDTH - 120, 20, Constants.SCREEN_WIDTH - 20, 40);
        currentHealth = new Rect(Constants.SCREEN_WIDTH - 120, 20, Constants.SCREEN_WIDTH - 20, 40);
    }

    public void setCurrentHealth(){
        currentHealth.set(currentHealth.left, currentHealth.top, Constants.SCREEN_WIDTH - 20 - getCurrentHealthDecrement(), currentHealth.bottom);
    }

    public int getCurrentHealthDecrement(){
        if (health.getInitial() != 0)
            return (health.getInitial() - health.getCurrent()) * 100 / health.getInitial();
        return 0;
    }

    public void getDamage(int damage){
        damage = (getProtection().getCurrent() > damage) ? 0: getProtection().getCurrent() - damage;
        getHealth().changeCurrent(damage);
        setCurrentHealth();
        startShowHealthTime = System.currentTimeMillis();
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

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public Rect getFullHealth() {
        return fullHealth;
    }

    public void setFullHealth(Rect fullHealth) {
        this.fullHealth = fullHealth;
    }

    public Rect getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Rect currentHealth) {
        this.currentHealth = currentHealth;
    }

    public long getStartShowHealthTime() {
        return startShowHealthTime;
    }

    public void setStartShowHealthTime(long startShowHealthTime) {
        this.startShowHealthTime = startShowHealthTime;
    }
}
