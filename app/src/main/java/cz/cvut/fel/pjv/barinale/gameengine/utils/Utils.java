package cz.cvut.fel.pjv.barinale.gameengine.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;

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

    public static boolean pointsAreEqual(Point pointOne, Point pointTwo){
        return pointOne.x == pointTwo.x && pointOne.y == pointTwo.y;
    }

    public static void saveGameState(Context context){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File
                    (context.getFilesDir() + File.separator + Constants.fileName)));
            //bufferedWriter.write("25");
            bufferedWriter.write("6 5");
            bufferedWriter.close();
        }
        catch (IOException e){

        }

    }

    public static void loadGameState(Context context){
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File
                    (context.getFilesDir() + File.separator + Constants.fileName)));
            while ((line = bufferedReader.readLine()) != null) {
                GameObjectManager.addObjectFromFile(line);
            }
        }
        catch (IOException e){
        }
    }
}
