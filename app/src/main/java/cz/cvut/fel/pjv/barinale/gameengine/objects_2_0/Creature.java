package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public abstract class Creature extends Entity{

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
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getMainImage(),
                getScreenCoordinates().x - getMainImage().getWidth()/2,
                getScreenCoordinates().y - getMainImage().getHeight()/2, null);
    }
/*
    @Override
    public void update(Point userPoint, Point mapPosition){
        Point direction = Utils.getDirection(userPoint, getMapCoordinates(),
                getSpeed().getCurrent());
        Point initialCoordinates = new Point(getMapCoordinates().x, getMapCoordinates().y);
        getMapCoordinates().x += direction.x;
        getMapCoordinates().y += direction.y;
        setBody();
        setActiveZone();
        if (CollisionDetecter.isCollide(this)) {
            getMapCoordinates().set(initialCoordinates.x, initialCoordinates.y);
            setBody();
            setActiveZone();
        }
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
    }
*/

    @Override
    public void update(Point userPoint, Point mapPosition){
        int angle = 0;
        Point initialDirection = Utils.getDirection(userPoint, getMapCoordinates(),
                getSpeed().getCurrent());
        Point direction = initialDirection;
        if (!Utils.stopNear(userPoint, this)) {
            do {
                Point initialCoordinates = new Point(getMapCoordinates().x, getMapCoordinates().y);
                getMapCoordinates().x += direction.x;
                getMapCoordinates().y += direction.y;
                setBody();
                setActiveZone();
                if (CollisionDetecter.isCollide(this)) {
                    getMapCoordinates().set(initialCoordinates.x, initialCoordinates.y);
                    setBody();
                    setActiveZone();
                    direction = Utils.rotateVector(initialDirection, angle += 45);
                } else {
                    break;
                }
            } while (angle < 360);
        }
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
    }

    public void attack(Entity entity) {
        if (System.currentTimeMillis() - getTimeSinceLastAttack() < getAttackDelay()) {
            return;
        }
        else {
            entity.getDamage(getStrength().getCurrent());
            setTimeSinceLastAttack(System.currentTimeMillis());
        }
    }
}
