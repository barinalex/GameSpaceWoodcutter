package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Creature;

public abstract class Enemy extends Creature {

    public Enemy(Point mapCoordinates) {
        super(mapCoordinates);
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        super.update(userPoint, mapPosition);
        if (nearThePlayer()){
            attack(EntityManager.player);
        }
    }
}
