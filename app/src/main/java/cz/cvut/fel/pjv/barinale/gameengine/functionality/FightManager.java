package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Player;

public class FightManager {
    public static boolean attackIsSuccess(GameObject gameObject, Player player, Point userPoint){
        if (gameObject.getBody() != null && gameObject.getBody().contains(userPoint.x, userPoint.y)){
            gameObject.decreaseHealth(player.getAttack());
            if (gameObject.getCharacteristics()[Constants.HEALTH] < 1){
                GameObjectManager.gameObjects.remove(gameObject);
            }
            return true;
        }
        return false;
    }

    public static void attackPlayer(Enemy enemy){
        if (enemy.getType() == Constants.ENEMY &&
                CollisionDetecter.readyToAttackPlayer(GameObjectManager.player, enemy) &&
                enemy.attack()){
            GameObjectManager.player.decreaseHealth(enemy.getCharacteristics()[Constants.STRENGHT]);
        }
    }
}
