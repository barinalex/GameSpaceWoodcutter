package cz.cvut.fel.pjv.barinale.gameengine.utils;

import android.content.Context;
import android.graphics.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creature;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Player2_0;

public class Utils {
    public static Point getDirection(Point newPoint, Point oldPoint, double rate){
        /*
         *  direction = rate * v chceme vector ve stejnem smeru jako v ale delky rate
         */
        int x = newPoint.x - (oldPoint.x);
        int y = newPoint.y - (oldPoint.y);
        if (Math.abs(x) < Math.abs(rate) && Math.abs(y) < Math.abs(rate)){
            return new Point(0, 0);
        }
        double abs = Math.sqrt((Math.pow((double) x, 2) + Math.pow((double) y, 2)));
        if (abs != 0) rate /= abs;
        return new Point((int)(x * rate), (int)(y * rate));
    }

    public static Point increaseVector(Point vector, int coefficient){
        return new Point(vector.x * coefficient, vector.y * coefficient);
    }

    public static Point rotateVector(Point vector, int angle){
        /*
         *  vynasobime vektor matice rotace
         */
        return new Point((int)(Math.cos(angle) * vector.x - Math.sin(angle) * vector.y),
                         (int)(Math.sin(angle) * vector.x + Math.cos(angle) * vector.y));
    }

    public static boolean isSmallerThan(Point Vector1, Point Vector2){
        return true;
    }

    public static boolean checkWinCondition(){
        for (GameObject gameObject: GameObjectManager.gameObjects){
            if (gameObject.getType() != Constants.ITEM && gameObject.getType() != Constants.PLAYER){
                return false;
            }
        }
        return true;
    }

    public static boolean stopNear(Point point, Creature creature){
        for (Entity entity: EntityManager.entities){
            if (!(entity instanceof Item)){
                if (entity.getBody().contains(point.x, point.y) && CollisionDetecter.isCollide(creature, entity)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean anObstacleOnTheWay(Point point){
        for (Entity entity: EntityManager.entities){
            if (!(entity instanceof Item)){
                if (entity.getBody().contains(point.x, point.y)){
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean checkGameOver(){
        return (GameObjectManager.player.getCharacteristics()[Constants.HEALTH] <= 0);
    }

    public static void saveGameState(Context context){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File
                    (context.getFilesDir(), Constants.savedGameFileName)));
            String line;
            bufferedWriter.write(GameObjectManager.background.getCoordinate().x + " " + GameObjectManager.background.getCoordinate().y + "\n");
            for (GameObject gameObject: GameObjectManager.gameObjects){
                line = gameObject.getType() + " " + gameObject.getMapCoordinates().x +
                        " " + gameObject.getMapCoordinates().y +
                        " " + gameObject.getInitialCharacteristics()[Constants.HEALTH] +
                        " " + gameObject.getInitialCharacteristics()[Constants.SPEED] +
                        " " + gameObject.getInitialCharacteristics()[Constants.STRENGHT]+
                        " " + gameObject.getCharacteristics()[Constants.HEALTH] +
                        " " + gameObject.getCharacteristics()[Constants.SPEED] +
                        " " + gameObject.getCharacteristics()[Constants.STRENGHT];
                for (GameObject item: gameObject.getInventory()){
                    line += " " + item.getType();
                }
                line += "\n";
                bufferedWriter.write(line);

            }
            bufferedWriter.close();
        }
        catch (IOException e){}
    }

    public static void loadGame(Context context, String fileName, boolean loadSavedState){
        String line;
        try {
            BufferedReader bufferedReader;
            if (loadSavedState) {
                bufferedReader = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            }
            else {
                bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            }

            GameObjectManager.initializeObjectsArray();

            String[] data = bufferedReader.readLine().split(" ");
            GameObjectManager.background = new Background(ImageArchive.images.get(Constants.BACKGROUND).get(0));
            GameObjectManager.background.setCoordinate(new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1])));

            while ((line = bufferedReader.readLine()) != null) {
                GameObjectManager.addObjectFromFile(line);
            }
        }
        catch (IOException e){
        }
    }
}
