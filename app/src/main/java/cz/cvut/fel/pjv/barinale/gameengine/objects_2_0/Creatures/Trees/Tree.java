package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Creature;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public abstract class Tree extends Creature {
    private boolean dead = false;
    public Tree(Point mapCoordinates) {
        super(mapCoordinates);
        setSpeed(new Characteristic(2));
        setStrength(new Characteristic(4));
        setAttackDelay(3000);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
        if (!isDead() && nearThePlayer()){
            attack(EntityManager.player);
        }
    }

    public void die(){
        if (!dead) {
            getSpeed().setCurrent(0);
            getMapCoordinates().set(getMapCoordinates().x, getMapCoordinates().y + getMainImage().getHeight() * 5 / 16);
            setMainImageId(R.drawable.dead_tree);
            setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
            dead = true;
        }
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
}
