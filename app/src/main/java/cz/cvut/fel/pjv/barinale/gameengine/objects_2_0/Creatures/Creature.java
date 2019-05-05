package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Potion;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public abstract class Creature extends Entity {

    private int attackDelay;
    private long timeSinceLastAttack;
    private long timeSinceLastAttackMotion;
    private long timeSinceLastMove;
    private ArrayList<Bitmap> moveImages;
    private ArrayList<Bitmap> attackImages;
    private int moveImageIndex;
    private int attackImageIndex;

    public int getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }

    public long getTimeSinceLastAttack() {
        return timeSinceLastAttack;
    }

    public void setTimeSinceLastAttack(long timeSinceLastAttack) {
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    public long getTimeSinceLastAttackMotion() {
        return timeSinceLastAttackMotion;
    }

    public void setTimeSinceLastAttackMotion(long timeSinceLastAttackMotion) {
        this.timeSinceLastAttackMotion = timeSinceLastAttackMotion;
    }

    public long getTimeSinceLastMove() {
        return timeSinceLastMove;
    }

    public void setTimeSinceLastMove(long timeSinceLastMove) {
        this.timeSinceLastMove = timeSinceLastMove;
    }

    public ArrayList<Bitmap> getMoveImages() {
        return moveImages;
    }

    public void setMoveImages(ArrayList<Bitmap> moveImages) {
        this.moveImages = moveImages;
    }

    public ArrayList<Bitmap> getAttackImages() {
        return attackImages;
    }

    public void setAttackImages(ArrayList<Bitmap> attackImages) {
        this.attackImages = attackImages;
    }

    public int getMoveImageIndex() {
        return moveImageIndex;
    }

    public void setMoveImageIndex(int moveImageIndex) {
        this.moveImageIndex = moveImageIndex;
    }

    public int getAttackImageIndex() {
        return attackImageIndex;
    }

    public void setAttackImageIndex(int attackImageIndex) {
        this.attackImageIndex = attackImageIndex;
    }

    public Creature(Point mapCoordinates) {
        super(mapCoordinates);
        setTimeSinceLastAttack(System.currentTimeMillis());
        moveImages = null;
        attackImages = null;
        timeSinceLastMove = 0;
        timeSinceLastAttackMotion = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        drawMove(canvas);
    }

    public void drawMove(Canvas canvas){
        if (moveImages == null){
            super.draw(canvas);
        }else {
            Bitmap image = moveImages.get(moveImageIndex);
            canvas.drawBitmap(image,
                    getScreenCoordinates().x - image.getWidth()/2,
                    getScreenCoordinates().y - image.getHeight()/2, null);
            if (System.currentTimeMillis() - timeSinceLastMove > 250){
                moveImageIndex++;
                moveImageIndex %= moveImages.size();
                timeSinceLastMove = System.currentTimeMillis();
            }
        }
    }

    public void drawAttack(Canvas canvas){

    }
    @Override
    public void update(Point userPoint, Point mapPosition){
        super.update(userPoint, mapPosition);
        int angle = 0;
        Point initialDirection = Utils.getDirection(userPoint, getMapCoordinates(),
                getSpeed().getCurrent());
        Point direction = initialDirection;
        //if (!Utils.stopNear(userPoint, this)) {
            //do {
                Point initialCoordinates = new Point(getMapCoordinates().x, getMapCoordinates().y);
                getMapCoordinates().x += direction.x;
                getMapCoordinates().y += direction.y;
                setBody();
                setActiveZone();
                if (CollisionDetecter.isCollide(this)) {
                    getMapCoordinates().set(initialCoordinates.x, initialCoordinates.y);
                    setBody();
                    setActiveZone();
                    //direction = Utils.rotateVector(initialDirection, angle += 45);
                }
                /*else {
                    break;
                }
            } while (angle < 360);*/
        //}
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
    }

    public void usePotion(Potion potion){
        getHealth().restore(potion.getHealth().getCurrent());
        getSpeed().restore(potion.getSpeed().getCurrent());
        getProtection().restore(potion.getProtection().getCurrent());
        getStrength().restore(potion.getStrength().getCurrent());
        setCurrentHealthIndicator();
        getInventory().remove(potion);
    }

    public void attack(Entity entity) {
        if (System.currentTimeMillis() - getTimeSinceLastAttack() > getAttackDelay()) {
            entity.getDamage(getStrength().getCurrent());
            setTimeSinceLastAttack(System.currentTimeMillis());
        }
    }

    public boolean nearThePlayer(){
        return Rect.intersects(getBody(), EntityManager.player.getActiveZone());
    }
}
