package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Axe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Enemy2_0;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Player2_0;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Tree;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Player;

public class EntityManager {
    public static Player2_0 player;
    public static Background background;
    public static ArrayList<Entity> entities;
    private static Random random = new Random();


    public static void createRandomMap(){
        entities = new ArrayList<>();
        background = new Background(BitmapFactory.decodeResource(Constants.resources, R.drawable.seamless));
        player = new Player2_0(new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2));
        entities.add(player);

        int amount = random.nextInt(5) + 2;
        Axe axe;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(axe = new Axe(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(axe);
        }
        amount = random.nextInt(15) + 5;
        Tree tree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(tree = new Tree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(tree);
        }
        amount = random.nextInt(5) + 2;
        Enemy2_0 enemy2_0;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(enemy2_0 = new Enemy2_0(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(enemy2_0);
        }
    }

    private static boolean isIntersected(Entity entity){
        for (Entity e: entities) {
            if (e != entity && Rect.intersects(e.getActiveZone(), entity.getActiveZone())) {
                return true;
            }
        }
        return false;
    }
}
