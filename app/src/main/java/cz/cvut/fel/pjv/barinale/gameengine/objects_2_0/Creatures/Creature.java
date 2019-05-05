package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures;

import android.graphics.Point;
import android.graphics.Rect;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Potion;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public abstract class Creature extends Entity {

    private int attackDelay;
    private long timeSinceLastAttack;

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

    public Creature(Point mapCoordinates) {
        super(mapCoordinates);
        setTimeSinceLastAttack(System.currentTimeMillis());
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
