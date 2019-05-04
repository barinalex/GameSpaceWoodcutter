package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public abstract class Tree extends Entity {
    public Tree(Point mapCoordinates) {
        super(mapCoordinates);
    }

    public void initializeTree(int ImageId, int initialHealth, int initialProtection){
        setMainImageId(ImageId);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(initialHealth));
        setProtection(new Characteristic(initialProtection));
    }

    @Override
    public void setBody() {
        getBody().set(getMapCoordinates().x - getMainImage().getWidth() / 8,
                getMapCoordinates().y + getMainImage().getHeight() / 4,
                getMapCoordinates().x + getMainImage().getWidth() / 8,
                getMapCoordinates().y + getMainImage().getHeight() * 3 / 8);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
        setActiveZone();
    }
}
