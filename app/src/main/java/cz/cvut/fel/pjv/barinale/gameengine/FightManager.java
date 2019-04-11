package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;

public class FightManager {
    public static void updateHealth(GameObject gameObject, Player player, Point userPoint){
        if (gameObject.getBody() != null && gameObject.getBody().contains(userPoint.x, userPoint.y)){
            gameObject.setCharacteristics(Constants.HEALTH, player.getCharacteristics()[Constants.STRENGHT]);
            if (gameObject.getCharacteristics()[Constants.HEALTH] < 1){
                GameObjectManager.gameObjects.remove(gameObject);
            }
        }
    }
}
