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

import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Player;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Runes.RedRune;

public class Utils {
    public static String savedGameFileName = "save.txt";
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

    public static boolean checkWinCondition(){
        for (Item item: EntityManager.player.getInventory()){
            if (item instanceof RedRune) return true;
        }
        return false;
    }

    public static void saveGameState(Context context) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File
                    (context.getFilesDir(), savedGameFileName)));
            bufferedWriter.write(EntityManager.background.getLocation() + " " +
                    EntityManager.background.getCoordinates().x + " " +
                    EntityManager.background.getCoordinates().y + "\n");
            for (Entity entity : EntityManager.entities){
                String line = entity.getClass().getSimpleName() + " " +
                        entity.getMapCoordinates().x + " " +
                        entity.getMapCoordinates().y + " " +
                        entity.getHealth().getCurrent();
                if (entity instanceof Player) {
                    for (Item item : entity.getInventory()) {
                        line += " " + item.getClass().getSimpleName();
                    }
                }
                line += "\n";
                bufferedWriter.write(line);
            }
            bufferedWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadGame(Context context, String fileName){
        String line;
        try {
            BufferedReader bufferedReader;
            if (fileName == null) {
                bufferedReader = new BufferedReader(new FileReader(new File(context.getFilesDir(), savedGameFileName)));
            }
            else {
                bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            }
            ArrayList<String> mapDescription = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                mapDescription.add(line);
            }
            EntityManager.createMap(mapDescription);
        }
        catch (IOException e){
        }
    }
}
