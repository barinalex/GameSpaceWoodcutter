package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class GameObjectManager {
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();;
    private static Random random = new Random();

    public static void addObjects(int houseNumber, int treesNumber, int axesNumber, int enemiesNumber){
        int green_t = random.nextInt(treesNumber) + 1;
        int blue_t = treesNumber - green_t;
        addObject(enemiesNumber, Constants.ENEMY);
        addObject(houseNumber, Constants.CABIN);
        addObject(blue_t, Constants.BLUETREE);
        addObject(green_t, Constants.GREENTREE);
        addObject(axesNumber, Constants.ITEM);
    }

    public static Player addPlayer(){
        int[] characteristics = {Constants.TYPE_HEALTH[Constants.PLAYER],
                                Constants.TYPE_SPEED[Constants.PLAYER],
                                Constants.TYPE_STRENGHT[Constants.PLAYER]};
        Player player = new Player(ImageArchive.images.get(Constants.PLAYER), new ArrayList<GameObject>(),
                new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2),
                characteristics, "" + Constants.PLAYER, Constants.PLAYER);
        gameObjects.add(player);
        return player;
    }

    private static void addObject(int number, int type){
        GameObject gameObject;
        for (int i = 0; i < number; i++) {
            int[] characteristics = {Constants.TYPE_HEALTH[type], Constants.TYPE_SPEED[type], Constants.TYPE_STRENGHT[type]};
            gameObject = new GameObject(ImageArchive.images.get(type), new ArrayList<GameObject>(),
                    new Point(random.nextInt(ImageArchive.map_size.x - ImageArchive.images.get(type).get(Constants.MAINIMAGE).getWidth()),
                            random.nextInt(ImageArchive.map_size.y - ImageArchive.images.get(type).get(Constants.MAINIMAGE).getHeight())),
                    characteristics, "" + type, type);
            if (isIntersected(gameObject)){
                i--;
                continue;
            }
            gameObjects.add(gameObject);
        }
    }

    private static boolean isIntersected(GameObject mainObject){
        for (GameObject gameObject: gameObjects) {
            if (gameObject != mainObject && Rect.intersects(gameObject.getActiveZone(), mainObject.getActiveZone())) {
                return true;
            }
        }
        return false;
    }
}
