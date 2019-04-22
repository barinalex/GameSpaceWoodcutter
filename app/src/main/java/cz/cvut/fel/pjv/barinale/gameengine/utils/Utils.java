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

import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;

public class Utils {
    public static Point get_direction(Point new_point, Point old_point, double rate){
        // direction = rate * v chceme vector ve stejnem smeru jako v ale delky rate
        int x = new_point.x - (old_point.x);
        int y = new_point.y - (old_point.y);
        if (Math.abs(x) < Math.abs(rate) && Math.abs(y) < Math.abs(rate)){
            return new Point(0, 0);
        }
        double abs = Math.sqrt((Math.pow((double) x, 2) + Math.pow((double) y, 2)));
        if (abs != 0) rate /= abs;
        return new Point((int)(x * rate), (int)(y * rate));
    }

    public static boolean checkWinCondition(ArrayList<GameObject> gameObjects){
        for (GameObject gameObject: GameObjectManager.gameObjects){
            if (gameObject.getType() != Constants.ITEM && gameObject.getType() != Constants.PLAYER){
                return false;
            }
        }
        return true;
    }

    public static boolean checkGameOver(){
        return (GameObjectManager.player.getCharacteristics()[Constants.HEALTH] < 1);
    }

    public static boolean pointsAreEqual(Point pointOne, Point pointTwo){
        return pointOne.x == pointTwo.x && pointOne.y == pointTwo.y;
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
                        " " + gameObject.getCharacteristics()[Constants.HEALTH] +
                        " " + gameObject.getCharacteristics()[Constants.SPEED] +
                        " " + gameObject.getCharacteristics()[Constants.STRENGHT] + "\n";
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
