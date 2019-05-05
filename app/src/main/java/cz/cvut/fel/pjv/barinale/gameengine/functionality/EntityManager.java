package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Background;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.FatSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.OneEye;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.Tree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.SimpleAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.GoldenAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.HealthPotion;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BlueWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BrownWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.CherryWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.GreenWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.OrangeWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.YellowWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Player;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Teleport;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.BlueTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.BrownTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.CherryTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.GreenTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.OrangeTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Trees.YellowTree;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;

public class EntityManager {
    public static Player player;
    public static Background background;
    public static ArrayList<Entity> entities;
    private static Random random = new Random();

    public static void createMap(ArrayList<String> mapDescription){
        entities = new ArrayList<>();
        String data[] = mapDescription.get(0).split(" ");
        background = new Background("black_land");
        background.setCoordinates(new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        mapDescription.remove(0);
        for (String line: mapDescription){
            data = line.split(" ");
            Entity entity = createEntity(data[0], new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            entity.getHealth().setCurrent(Integer.parseInt(data[3]));
            entity.setCurrentHealthIndicator();
            for (int i = 4; i < data.length; i++) {
                Item item = (Item) createEntity(data[i], new Point(entity.getMapCoordinates().x, entity.getMapCoordinates().y));
                entity.getInventory().add(item);
                entity.addItemEffects(item);
            }
            entities.add(entity);
            if (entity instanceof Player) player = (Player) entity;
        }
    }

    public static void createRandomMap(String location, boolean teleported){
        entities = new ArrayList<>();
        background = new Background(location);
        if (!teleported)
            player = new Player(new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2));
        entities.add(new Teleport(new Point(player.getMapCoordinates().x, player.getMapCoordinates().y + player.getMainImage().getHeight() / 2)));
        entities.add(player);

        addEntityRandomPos(1, SimpleAxe.class.getSimpleName());
        addEntityRandomPos(2, HealthPotion.class.getSimpleName());
        addEntityRandomPos(1, GoldenAxe.class.getSimpleName());
        addEntityRandomPos(4, BlueTree.class.getSimpleName());
        addEntityRandomPos(4, BrownTree.class.getSimpleName());
        if (location.equals("cherry_land")) {
            addEntityRandomPos(4, CherryTree.class.getSimpleName());
        }
        addEntityRandomPos(8, GreenTree.class.getSimpleName());
        addEntityRandomPos(3, OrangeTree.class.getSimpleName());
        if (location.equals("yellow_land")) {
            addEntityRandomPos(4, YellowTree.class.getSimpleName());
        }
        addEntityRandomPos(5, OneEye.class.getSimpleName());
        addEntityRandomPos(2, FatSlug.class.getSimpleName());
    }

    private static void addEntityRandomPos(int amount, String type){
        Entity entity;
        int cyckleCounter;
        for (int i = 0; i < amount; i++) {
            cyckleCounter = 0;
            while (isIntersected(entity = createEntity(type, null)) && cyckleCounter++ < 5);
            entities.add(entity);
        }
    }

    private static Entity createEntity(String type, Point coordinates){
        if (coordinates == null){
            coordinates = new Point(random.nextInt(ImageArchive.map_size.x - 100) + 50, random.nextInt(ImageArchive.map_size.y - 100) + 50);
        }
        switch (type){
            case "Player":
                return new Player(coordinates);
            case "SimpleAxe":
                return new SimpleAxe(coordinates);
            case "GoldenAxe":
                return new GoldenAxe(coordinates);
            case "HealthPotion":
                return new HealthPotion(coordinates);
            case "BlueTree":
                return new BlueTree(coordinates);
            case "BrownTree":
                return new BrownTree(coordinates);
            case "CherryTree":
                return new CherryTree(coordinates);
            case "GreenTree":
                return new GreenTree(coordinates);
            case "OrangeTree":
                return new OrangeTree(coordinates);
            case "YellowTree":
                return new YellowTree(coordinates);
            case "BlueWood":
                return new BlueWood(coordinates);
            case "BrownWood":
                return new BrownWood(coordinates);
            case "CherryWood":
                return new CherryWood(coordinates);
            case "GreenWood":
                return new GreenWood(coordinates);
            case "OrangeWood":
                return new OrangeWood(coordinates);
            case "YellowWood":
                return new YellowWood(coordinates);
            case "Teleport":
                return new Teleport(coordinates);
            case "OneEye":
                return new OneEye(coordinates);
            case "FatSlug":
                return new FatSlug(coordinates);
        }
        return null;
    }

    public static void removeDeadBodyies(){
        for (Entity entity: entities){
            if (!(entity instanceof Item) && !(entity instanceof Teleport) && entity.isDead()) {
                for (Item item : entity.getInventory()) {
                    item.setMapCoordinates(new Point(entity.getMapCoordinates().x,
                            entity.getMapCoordinates().y));
                    item.setBody();
                    item.setActiveZone();
                    entities.add(1, item);
                    entity.getInventory().remove(item);
                }
                if (entity instanceof Tree){
                    ((Tree) entity).die();
                }
                else {
                    entities.remove(entity);
                }
            }
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
