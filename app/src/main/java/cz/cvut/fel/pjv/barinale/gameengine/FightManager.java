package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;

public class FightManager {
    public static void decreaseHealth(GameObject gameObject, Player player, Point userPoint){
        if (gameObject.getBody() != null && gameObject.getBody().contains(userPoint.x, userPoint.y)){
            gameObject.decreaseHealth(player.getCharacteristics()[Constants.STRENGHT]);
            if (gameObject.getCharacteristics()[Constants.HEALTH] < 1){
                GameObjectManager.gameObjects.remove(gameObject);
            }
        }
    }

    public static void attackPlayer(Enemy enemy){
        if (enemy.getType() == Constants.ENEMY &&
                CollisionDetecter.readyToAttackPlayer(GameObjectManager.player, enemy) &&
                enemy.attack()){
            GameObjectManager.player.decreaseHealth(enemy.getCharacteristics()[Constants.STRENGHT]);
        }
    }
}
