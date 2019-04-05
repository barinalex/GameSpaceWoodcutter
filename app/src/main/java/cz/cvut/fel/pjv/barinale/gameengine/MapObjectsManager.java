package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class MapObjectsManager {
    public static ArrayList<MapObject> mapObjects;
    public static int[][] on_create = {{1, 100, 100}, {2, 200, 1500}, {5, 700, 1200}, {2, 1300, 1000}};
    public static String[] names = {"cabin", "blue tree", "green tree", "blue tree2"};

    public static void place_on_map(){
        mapObjects = new ArrayList<>();
        int objects_number = names.length;
        for (int i = 0; i < objects_number; i++) {
            mapObjects.add(new MapObject(ImageArchive.images.get(on_create[i][0]),
                    new Point(on_create[i][1], on_create[i][2]), names[i], on_create[i][0]));
        }
    }

    public static void random_map(int house_number, int trees_number){
        mapObjects = new ArrayList<>();
        int type;
        Random random = new Random();
        MapObject mapObject;
        for (int i = 0; i < trees_number; i++) {
            type = random.nextInt(2) + 2;
            mapObject = new MapObject(ImageArchive.images.get(type),
                    new Point(random.nextInt(ImageArchive.map_size.x),
                            random.nextInt(ImageArchive.map_size.y)), "" + type, type);
            for (MapObject mo: mapObjects){
                if (Rect.intersects(mapObject.getForbidden_zone(), mo.getActive_zone())){
                    i--;
                    continue;
                }
            }
            mapObjects.add(mapObject);
        }

        for (int i = 0; i < house_number; i++){
            mapObject = new MapObject(ImageArchive.images.get(1),
                    new Point(random.nextInt(ImageArchive.map_size.x),
                            random.nextInt(ImageArchive.map_size.y)), "" + 1, 1);
            for (MapObject mo: mapObjects){
                if (Rect.intersects(mapObject.getForbidden_zone(), mo.getActive_zone())){
                    i--;
                    continue;
                }
            }
            mapObjects.add(mapObject);
        }
    }
}
