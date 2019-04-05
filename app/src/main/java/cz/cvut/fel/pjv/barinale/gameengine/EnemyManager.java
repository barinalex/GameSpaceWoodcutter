package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class EnemyManager {
    public static ArrayList<Protagonist> enemies;
    public static int[][] on_create = {{4, 800, 800}, {4, 900, 1200}, {4, 1000, 500}, {4, 700, 1500}};
    public static String[] names = {"1", "2", "3", "4"};

    public static void place_on_map(){
        enemies = new ArrayList<>();
        int objects_number = names.length;
        for (int i = 0; i < objects_number; i++) {
            enemies.add(new Protagonist(ImageArchive.images.get(on_create[i][0]),
                    new Point(on_create[i][1], on_create[i][2]), names[i]));
        }
    }

    public static void random_spawn(int enemies_number, ArrayList<MapObject> mapObjects){
        enemies = new ArrayList<>();
        Random random = new Random();
        Protagonist protagonist;
        for (int i = 0; i < enemies_number; i++) {
            protagonist = new Protagonist(ImageArchive.images.get(4),
                    new Point(random.nextInt(ImageArchive.map_size.x),
                            random.nextInt(ImageArchive.map_size.y)), "" + 4);
            for (MapObject mapObject: mapObjects){
                if (mapObject.getForbidden_zone() != null && Rect.intersects(protagonist.getBody(), mapObject.getForbidden_zone())){
                    i--;
                    continue;
                }
            }
            for (Protagonist pr: enemies){
                if (Rect.intersects(pr.getBody(), protagonist.getBody())){
                    i--;
                    continue;
                }
            }
            enemies.add(protagonist);
        }
    }
}
