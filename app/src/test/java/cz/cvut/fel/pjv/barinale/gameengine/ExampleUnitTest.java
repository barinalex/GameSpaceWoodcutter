package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;
import org.junit.*;
import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.FatSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.RedSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Player;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.SimpleAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.FatSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Teleport;
import cz.cvut.fel.pjv.barinale.gameengine.world.WorldCreator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Before
    public void setUp(){
        Point point = new Point(0,0);
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void playerAttackTest(){
        FatSlug fatSlug = new FatSlug(new Point(0 , 0));
        Player player = new Player(new Point(0,0));
        player.setAttackDelay(-1);
        player.attack(fatSlug);
        assertEquals(fatSlug.getHealth().getInitial() +
                fatSlug.getProtection().getCurrent() -
                player.getStrength().getCurrent(),
                fatSlug.getHealth().getCurrent());
    }
    @Test
    public void entityGetDamage(){
        FatSlug fatSlug = new FatSlug(new Point(0 , 0));
        fatSlug.getDamage(5);
        assertEquals(fatSlug.getHealth().getInitial() - 5, fatSlug.getHealth().getCurrent());
    }
    @Test
    public void playerEat(){
        Player player = new Player(new Point(0,0));
        player.getDamage(5);
        FatSlugCorpus fatSlugCorpus = new FatSlugCorpus(new Point(0,0));
        player.eat(fatSlugCorpus);
        assertEquals(player.getHealth().getInitial(), player.getHealth().getCurrent());
    }
    @Test
    public void playerGetDamage(){
        Player player = new Player(new Point(0,0));
        player.getDamage(5);
        assertEquals(player.getHealth().getInitial() +
                player.getProtection().getCurrent() - 5, player.getHealth().getCurrent());
    }
    @Test
    public void worldCreation(){
        WorldCreator.createRandomMap("earth_land", false);
        boolean redSlugCreated = false;
        for (Entity entity: WorldCreator.entities){
            if (entity instanceof RedSlug){
                redSlugCreated = true;
                break;
            }
        }
        assertTrue(redSlugCreated);
    }
    @Test
    public void createEntity(){
        FatSlug fatSlug = new FatSlug(new Point());
        WorldCreator.entities = new ArrayList<>();
        WorldCreator.entities.add(fatSlug);
        fatSlug.getHealth().setCurrent(0);
        WorldCreator.removeDeadEntities();
        boolean noFatSlugs = true;
        for (Entity entity: WorldCreator.entities){
            if (entity instanceof FatSlug) {
                noFatSlugs = false;
                break;
            }
        }
        assertTrue(noFatSlugs);
    }
    @Test
    public void collisionDetection(){
        FatSlug fatSlug = new FatSlug(new Point(0, 0));
        FatSlug fatSlug2 = new FatSlug(new Point(1000, 1000));
        WorldCreator.entities = new ArrayList<>();
        WorldCreator.entities.add(fatSlug);
        WorldCreator.entities.add(fatSlug2);
        assertFalse(WorldCreator.isCollide(fatSlug));
    }
    @Test
    public void discardItem(){
        Player player = new Player(new Point());
        WorldCreator.entities = new ArrayList<>();
        WorldCreator.entities.add(player);
        player.getInventory().add(new SimpleAxe(new Point()));
        player.discardItem(0);
        assertTrue(player.getInventory().isEmpty());
    }
    @Test
    public void createMapFromFile(){
        ArrayList<String> mapDescription = new ArrayList<>();
        mapDescription.add("black_land 0 0");
        mapDescription.add("Teleport 400 400 0");
        mapDescription.add("Player 400 400 10");
        WorldCreator.createMap(mapDescription);
        assertTrue((WorldCreator.entities.get(0) instanceof Teleport) &&
                WorldCreator.entities.get(1) instanceof Player);
    }
}