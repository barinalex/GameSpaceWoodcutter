package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Player;

public class GameObjectManager {
    public static Player player;
    public static Background background;
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<Entity> entities;
    private static Random random = new Random();

    private static final int TYPE = 0;
    private static final int X = 1;
    private static final int Y = 2;
    private static final int HEALTH = 3;
    private static final int SPEED = 4;
    private static final int STRENGHT = 5;
    private static final int HEALTH_CUR = 6;
    private static final int SPEED_CUR = 7;
    private static final int STRENGHT_CUR = 8;

    public static void initializeObjectsArray(){
        gameObjects = new ArrayList<>();
    }

    public static void addObjectFromFile(String input){
        String[] objDescription = input.split(" ");
        int type = Integer.parseInt(objDescription[TYPE]);
        Point mapCoord = new Point(Integer.parseInt(objDescription[X]),Integer.parseInt(objDescription[Y]));
        int[] initialCharacteristics = {Integer.parseInt(objDescription[HEALTH]), Integer.parseInt(objDescription[SPEED]), Integer.parseInt(objDescription[STRENGHT])};
        int[] characteristics = {Integer.parseInt(objDescription[HEALTH_CUR]), Integer.parseInt(objDescription[SPEED_CUR]), Integer.parseInt(objDescription[STRENGHT_CUR])};
        ArrayList<GameObject> inventory = new ArrayList<>();

        int itemType;
        int[] itemCharacteristics = new int[3];
        for (int i = 9; i < objDescription.length; i++) {
            itemType = Integer.parseInt(objDescription[i]);
            itemCharacteristics[0] = Constants.TYPE_HEALTH[itemType];
            itemCharacteristics[1] = Constants.TYPE_SPEED[itemType];
            itemCharacteristics[2] = Constants.TYPE_STRENGHT[itemType];
            inventory.add(new GameObject(ImageArchive.images.get(itemType), new ArrayList<GameObject>(), mapCoord,
                    itemCharacteristics, itemCharacteristics, "" + itemType, itemType));
        }

        switch (type){
            case Constants.ENEMY:
                gameObjects.add(new Enemy(ImageArchive.images.get(type), inventory, mapCoord,
                        characteristics, initialCharacteristics, "" + type, type));
                break;
            case Constants.PLAYER:
                player = new Player(ImageArchive.images.get(type), inventory, mapCoord,
                        characteristics, initialCharacteristics, "" + type, type);
                gameObjects.add(player);
                break;
            default:
                gameObjects.add(new GameObject(ImageArchive.images.get(type), inventory, mapCoord,
                        characteristics, initialCharacteristics, "" + type, type));
                break;
        }
    }

    public static void createRandomMap(){
        background = new Background(ImageArchive.images.get(Constants.BACKGROUND).get(0));
        addObjects(random.nextInt(5),random.nextInt(20),random.nextInt(10),random.nextInt(10));
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
                characteristics, characteristics.clone(), "" + Constants.PLAYER, Constants.PLAYER);
        gameObjects.add(player);
    }

    private static void addEnemy(int number){
        Enemy enemy;
        for (int i = 0; i < number; i++){
            int[] characteristics = {Constants.TYPE_HEALTH[Constants.ENEMY], Constants.TYPE_SPEED[Constants.ENEMY], Constants.TYPE_STRENGHT[Constants.ENEMY]};
            enemy = new Enemy(ImageArchive.images.get(Constants.ENEMY), new ArrayList<GameObject>(),
                    new Point(random.nextInt(ImageArchive.map_size.x - ImageArchive.images.get(Constants.ENEMY).get(Constants.MAINIMAGE).getWidth()),
                            random.nextInt(ImageArchive.map_size.y - ImageArchive.images.get(Constants.ENEMY).get(Constants.MAINIMAGE).getHeight())),
                    characteristics, characteristics.clone(), "" + Constants.ENEMY, Constants.ENEMY);
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
                    characteristics, characteristics.clone(), "" + type, type);
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
