package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Point;

import org.junit.Test;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.FatSlug;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Player;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.FatSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.world.WorldCreator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
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
    public void playerHeal(){
        Player player = new Player(new Point(0,0));
        player.getDamage(5);
        FatSlugCorpus fatSlugCorpus = new FatSlugCorpus(new Point(0,0));
        player.eat(fatSlugCorpus);
        assertEquals(player.getHealth().getInitial(), player.getHealth().getCurrent());
    }
    @Test
    public void playerGetDamege(){
        Player player = new Player(new Point(0,0));
        player.getDamage(5);
        assertEquals(player.getHealth().getInitial() +
                player.getProtection().getCurrent() - 5, player.getHealth().getCurrent());
    }
    @Test
    public void worldCreation(){
        WorldCreator.createRandomMap("earth_land", false);
        assertTrue(WorldCreator.entities != null);
    }
}