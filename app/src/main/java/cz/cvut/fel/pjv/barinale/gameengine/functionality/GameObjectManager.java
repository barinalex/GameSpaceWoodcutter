package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Player;

public class GameObjectManager {
    public static Player player;
    public static Background background;
    public static ArrayList<GameObject> gameObjects;
    private static Random random = new Random();

    public static boolean loadFromFile = false;

    public static void initializeObjectsArray(){
        gameObjects = new ArrayList<>();
        //addPlayer();
    }

    public static void addObjectFromFile(String input){
        /*
        String[] data = input.split(" ");
        int type = Integer.parseInt(data[0]);
        int objectsNumber = Integer.parseInt(data[1]);
        addObject(objectsNumber, type);
        */
        String[] data = input.split(" ");
        int type = Integer.parseInt(data[0]);
        Point mapCoord = new Point(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
        int[] characteristics = {Constants.TYPE_HEALTH[type], Constants.TYPE_SPEED[type], Constants.TYPE_STRENGHT[type]};
        if (type != Constants.ENEMY) {
            GameObject gameObject = new GameObject(ImageArchive.images.get(type), new ArrayList<GameObject>(), mapCoord,
                    characteristics, "" + type, type);
            gameObjects.add(gameObject);
        }
        else {
            Enemy gameObject = new Enemy(ImageArchive.images.get(type), new ArrayList<GameObject>(), mapCoord,
                    characteristics, "" + type, type);
            gameObjects.add(gameObject);

        }
    }


    public static void addObjects(int houseNumber, int treesNumber, int axesNumber, int enemiesNumber){
        gameObjects = new ArrayList<>();
        int green_t = random.nextInt(treesNumber) + 1;
        int blue_t = treesNumber - green_t;
        addPlayer();
        addEnemy(enemiesNumber);
        addObject(houseNumber, Constants.CABIN);
        addObject(blue_t, Constants.BLUETREE);
        addObject(green_t, Constants.GREENTREE);
        addObject(axesNumber, Constants.ITEM);
    }

    public static void addPlayer(){
        int[] characteristics = {Constants.TYPE_HEALTH[Constants.PLAYER],
                                Constants.TYPE_SPEED[Constants.PLAYER],
                                Constants.TYPE_STRENGHT[Constants.PLAYER]};
        player = new Player(ImageArchive.images.get(Constants.PLAYER), new ArrayList<GameObject>(),
                new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2),
                characteristics, "" + Constants.PLAYER, Constants.PLAYER);
        gameObjects.add(player);
    }

    private static void addEnemy(int number){
        Enemy enemy;
        for (int i = 0; i < number; i++){
            int[] characteristics = {Constants.TYPE_HEALTH[Constants.ENEMY], Constants.TYPE_SPEED[Constants.ENEMY], Constants.TYPE_STRENGHT[Constants.ENEMY]};
            enemy = new Enemy(ImageArchive.images.get(Constants.ENEMY), new ArrayList<GameObject>(),
                    new Point(random.nextInt(ImageArchive.map_size.x - ImageArchive.images.get(Constants.ENEMY).get(Constants.MAINIMAGE).getWidth()),
                            random.nextInt(ImageArchive.map_size.y - ImageArchive.images.get(Constants.ENEMY).get(Constants.MAINIMAGE).getHeight())),
                    characteristics, "" + Constants.ENEMY, Constants.ENEMY);
            if (isIntersected(enemy)){
                i--;
                continue;
            }
            gameObjects.add(enemy);
        }
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
