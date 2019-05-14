package cz.cvut.fel.pjv.barinale.gameengine.world;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.FatSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.OneEye;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.RedSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.FatSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.OneEyeCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.RedSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Runes.RedRune;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls.CherryScroll;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls.EarthScroll;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls.MissionScroll;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls.YellowScroll;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.Tree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.SimpleAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.GoldenAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BlueWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BrownWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.CherryWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.GreenWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.OrangeWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.YellowWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Player;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Teleport;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.BlueTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.BrownTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.CherryTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.GreenTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.OrangeTree;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Trees.YellowTree;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

/**
 *
 */
public class WorldCreator {
    public static Player player;
    public static Background background;
    public static ArrayList<Entity> entities;
    private static Random random = new Random();

    /**
     *
     * @param mapDescription
     */
    public static void createMap(ArrayList<String> mapDescription){
        entities = new ArrayList<>();
        String data[] = mapDescription.get(0).split(" ");
        background = new Background(data[0]);
        background.setCoordinates(new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
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

    /**
     *
     * @param location
     * @param teleported
     */
    public static void createRandomMap(String location, boolean teleported){
        entities = new ArrayList<>();
        background = new Background(location);
        if (!teleported) {
            createRandomPlayer();
        }
        entities.add(createTeleport());
        entities.add(player);
        switch (location){
            case "black_land":
                createRandomBlackLand();
                break;
            case "yellow_land":
                createRandomYellowLand();
                break;
            case "cherry_land":
                createRandomCherryLand();
                break;
            case "earth_land":
                createRandomEarthLand();
                break;
        }
    }

    private static Teleport createTeleport(){
        return new Teleport(new Point(player.getMapCoordinates().x,
                player.getMapCoordinates().y + player.getSize().getHeight() / 2));
    }

    private static void createRandomPlayer(){
        player = new Player(new Point(GamePanel.SCREEN_WIDTH / 2, GamePanel.SCREEN_HEIGHT / 2));
        player.getInventory().add(new YellowScroll(new Point()));
        player.getInventory().add(new CherryScroll(new Point()));
        player.getInventory().add(new EarthScroll(new Point()));
        player.getInventory().add(new MissionScroll(new Point()));
    }

    private static void createRandomEarthLand(){
        addEntityRandomPos(15, GreenTree.class.getSimpleName());
        addEntityRandomPos(15, BrownTree.class.getSimpleName());
        addEntityRandomPos(10, OneEye.class.getSimpleName());
        addEntityRandomPos(10, FatSlug.class.getSimpleName());
        addEntityRandomPos(1, RedSlug.class.getSimpleName());
    }

    private static void createRandomCherryLand(){
        addEntityRandomPos(2, GreenTree.class.getSimpleName());
        addEntityRandomPos(2, BrownTree.class.getSimpleName());
        addEntityRandomPos(2, BlueTree.class.getSimpleName());
        addEntityRandomPos(2, OrangeTree.class.getSimpleName());
        addEntityRandomPos(20, CherryTree.class.getSimpleName());
        addEntityRandomPos(10, OneEye.class.getSimpleName());
        addEntityRandomPos(6, FatSlug.class.getSimpleName());
    }

    private static void createRandomYellowLand(){
        addEntityRandomPos(3, GreenTree.class.getSimpleName());
        addEntityRandomPos(5, BrownTree.class.getSimpleName());
        addEntityRandomPos(3, BlueTree.class.getSimpleName());
        addEntityRandomPos(10, OrangeTree.class.getSimpleName());
        addEntityRandomPos(15, YellowTree.class.getSimpleName());
        addEntityRandomPos(5, OneEye.class.getSimpleName());
        addEntityRandomPos(4, FatSlug.class.getSimpleName());
    }

    private static void createRandomBlackLand(){
        addEntityRandomPos(10, GreenTree.class.getSimpleName());
        addEntityRandomPos(3, BrownTree.class.getSimpleName());
        addEntityRandomPos(10, BlueTree.class.getSimpleName());
        addEntityRandomPos(2, OrangeTree.class.getSimpleName());
        addEntityRandomPos(5, OneEye.class.getSimpleName());
        addEntityRandomPos(2, FatSlug.class.getSimpleName());
    }

    private static void addEntityRandomPos(int amount, String type){
        Entity entity;
        for (int i = 0; i < amount; i++) {
            while (isIntersected(entity = createEntity(type, null)));
            entities.add(entity);
        }
    }

    private static Entity createEntity(String type, Point coordinates){
        if (coordinates == null){
            coordinates = new Point(random.nextInt(background.getMapSize().getWidth() - 200) + 100,
                    random.nextInt(background.getMapSize().getHeight() - 200) + 100);
        }
        switch (type){
            case "Player":
                return new Player(coordinates);
            case "SimpleAxe":
                return new SimpleAxe(coordinates);
            case "GoldenAxe":
                return new GoldenAxe(coordinates);
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
            case "RedSlug":
                return new RedSlug(coordinates);
            case "FatSlugCorpus":
                return new FatSlugCorpus(coordinates);
            case "RedSlugCorpus":
                return new RedSlugCorpus(coordinates);
            case "OneEyeCorpus":
                return new OneEyeCorpus(coordinates);
            case "YellowScroll":
                return new YellowScroll(coordinates);
            case "CherryScroll":
                return new CherryScroll(coordinates);
            case "EarthScroll":
                return new EarthScroll(coordinates);
            case "MissionScroll":
                return new MissionScroll(coordinates);
            case "RedRune":
                return new RedRune(coordinates);
        }
        return null;
    }

    /**
     * remove dead objects and add their items and dead bodies to the map
     */
    public static void removeDeadEntities(){
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

    /**
     *
     * @param entity
     * @return
     */
    public static boolean isCollide(Entity entity){
        if (entity.getBody() != null) {
            for (Entity e : WorldCreator.entities) {
                if (e != entity && !(e instanceof Item) && !(e instanceof Teleport) && Rect.intersects(e.getBody(), entity.getBody())) {
                    return true;
                }
            }
        }
        return false;
    }
}
