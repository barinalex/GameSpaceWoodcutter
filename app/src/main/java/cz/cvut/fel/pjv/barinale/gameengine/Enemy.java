package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class Enemy extends GameObject{
    private Rect searchingZone;
    private int attackDelay;
    private long timeSinceLastAttack;
    private int serchingZoneSize = 4;

    public Rect getSearchingZone() {
        return searchingZone;
    }

    public long getTimeSinceLastAttack() {
        return timeSinceLastAttack;
    }

    public void setTimeSinceLastAttack(int timeSinceLastAttack) {
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    public Enemy(ArrayList<Bitmap> images, ArrayList<GameObject> inventory,
                 Point mapCoordinates, int[] characteristics, String name, int type) {
        super(images, inventory, mapCoordinates, characteristics, name, type);
        attackDelay = 1000;
        timeSinceLastAttack = 0;
    }

    public boolean attack() {
        if (System.currentTimeMillis() - timeSinceLastAttack > attackDelay){
            timeSinceLastAttack = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    @Override
    public void setZones() {
        super.setZones();
        if (searchingZone == null){
            searchingZone = new Rect();
        }
        searchingZone.set(getMapCoordinates().x - getImageWidth()*serchingZoneSize,
                getMapCoordinates().y - getImageWidth()*serchingZoneSize,
                getMapCoordinates().x + getImageWidth()*serchingZoneSize,
                getMapCoordinates().y + getImageWidth()*serchingZoneSize);
    }

    @Override
    public void update(Point point, Point mapPosition) {
        if (Rect.intersects(GameObjectManager.player.getBody(), searchingZone)) {
            Point direction = Utils.get_direction(point, getMapCoordinates(),
                    getCharacteristics()[Constants.SPEED]);
            Point old_coordinates = new Point(getMapCoordinates().x, getMapCoordinates().y);
            getMapCoordinates().x += direction.x;
            getMapCoordinates().y += direction.y;
            setZones();
            if (CollisionDetecter.chechCollision(this)) {
                getMapCoordinates().set(old_coordinates.x, old_coordinates.y);
                setZones();
            }
        }
        else {
            setZones();
        }
        setScreenCoordinates(new Point(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y));
    }
}
