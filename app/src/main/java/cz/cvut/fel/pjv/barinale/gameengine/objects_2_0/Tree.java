package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Tree extends Entity{
    public Tree(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.green_tree_r);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        Random random = new Random();
        int health = random.nextInt(10) + 5;
        setHealth(new Characteristic(health, health));
        setProtection(new Characteristic(2, 2));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getMainImage(),
                getScreenCoordinates().x - getMainImage().getWidth()/2,
                getScreenCoordinates().y - getMainImage().getHeight()/2, null);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
        setActiveZone();
        if (getHealth().getCurrent() == 0){
            EntityManager.entities.remove(this);
        }
    }
}
