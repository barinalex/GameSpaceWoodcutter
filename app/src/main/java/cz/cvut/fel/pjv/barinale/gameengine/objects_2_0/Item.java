package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;

public abstract class Item extends Entity{

    public Item(Point mapCoordinates) {
        super(mapCoordinates);
        setHealth(new Characteristic(0,0));
        setSpeed(new Characteristic(0,0));
        setProtection(new Characteristic(0, 0));
        setStrength(new Characteristic(0, 0));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
        setBody();
        setActiveZone();
    }
}
