package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;

import java.util.ArrayList;

public class Utils {
    public static Point get_direction(Point new_point, Point old_point, double rate){
        // direction = rate * v chceme vector ve stejnem smeru jako v ale delky rate
        int x = new_point.x - (old_point.x);
        int y = new_point.y - (old_point.y);
        double abs = Math.sqrt((Math.pow((double) x, 2) + Math.pow((double) y, 2)));
        if (abs != 0) rate /= abs;
        return new Point((int)(x * rate), (int)(y * rate));
    }

    public static boolean checkWinCondition(ArrayList<GameObject> gameObjects){
        return gameObjects.size() == 1;
    }

    public static boolean checkGameOver(){
        return (GameObjectManager.player.getCharacteristics()[Constants.HEALTH] < 1);
    }
}
