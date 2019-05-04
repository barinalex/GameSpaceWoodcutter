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
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.BlueTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.BrownTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.CherryTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.GreenTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.OrangeTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.Tree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.YellowTree;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;

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

        int amount = random.nextInt(1) + 1;
        Axe axe;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(axe = new Axe(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(axe);
        }
        amount = random.nextInt(5) + 2;
        Enemy2_0 enemy2_0;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(enemy2_0 = new Enemy2_0(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(enemy2_0);
        }
        amount = 5;
        GreenTree greenTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(greenTree = new GreenTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(greenTree);
        }
        amount = 4;
        BrownTree brownTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(brownTree = new BrownTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(brownTree);
        }
        amount = 3;
        OrangeTree orangeTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(orangeTree = new OrangeTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(orangeTree);
        }
        amount = 2;
        BlueTree blueTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(blueTree = new BlueTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(blueTree);
        }
        amount = 2;
        YellowTree yellowTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(yellowTree = new YellowTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(yellowTree);
        }
        amount = 1;
        CherryTree cherryTree;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(cherryTree = new CherryTree(new Point(random.nextInt(ImageArchive.map_size.x), random.nextInt(ImageArchive.map_size.y)))));
            entities.add(cherryTree);
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
