package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public abstract class Tree extends Entity {
    private boolean dead = false;

    /**
     *
     * @param mapCoordinates
     */
    public Tree(Point mapCoordinates) {
        super(mapCoordinates);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
    }

    /**
     * change main image to dead tree, set new body and dead state
     */
    public void die(){
        if (!dead) {
            getSpeed().setCurrent(0);
            getMapCoordinates().set(getMapCoordinates().x, getMapCoordinates().y + getMainImage().getHeight() * 5 / 16);
            setMainImageId(R.drawable.dead_tree);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
            setActiveZone(new Rect());
            dead = true;
        }
    }

    /**
     *
     * @param ImageId
     * @param initialHealth
     * @param initialProtection
     */
    public void initializeTree(int ImageId, int initialHealth, int initialProtection){
        setSize(new Size(96, 128));
        if (GamePanel.resources != null) {
            setMainImageId(ImageId);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
            setSize(new Size(getMainImage().getWidth(), getMainImage().getHeight()));
        }
        setBody();
        setActiveZone();
        setHealth(new Characteristic(initialHealth));
        setProtection(new Characteristic(initialProtection));
    }

    @Override
    public void setBody() {
        getBody().set(getMapCoordinates().x - getSize().getWidth() / 8,
                getMapCoordinates().y + getSize().getHeight() / 4,
                getMapCoordinates().x + getSize().getWidth() / 8,
                getMapCoordinates().y + getSize().getHeight() * 3 / 8);
    }

    @Override
    public void setActiveZone() {
        getActiveZone().set(getMapCoordinates().x - getSize().getWidth() / 2,
                getMapCoordinates().y - getSize().getHeight() / 2,
                getMapCoordinates().x + getSize().getWidth() / 2,
                getMapCoordinates().y + getSize().getHeight() / 2);
    }
}
