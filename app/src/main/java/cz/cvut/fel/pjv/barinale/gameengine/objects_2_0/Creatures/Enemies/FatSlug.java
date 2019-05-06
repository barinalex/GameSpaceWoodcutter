package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.GoldenAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition.SimpleAxe;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.FatSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class FatSlug extends Enemy{
    private static Random random = new Random();
    public FatSlug(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.fat_slug);
        setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        setMoveImages(new ArrayList<Bitmap>());
        getMoveImages().add(getMainImage());
        getMoveImages().add(BitmapFactory.decodeResource(GamePanel.resources, R.drawable.fat_slug_step));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(10));
        setSpeed(new Characteristic(7));
        setProtection(new Characteristic(0));
        setStrength(new Characteristic(4));
        setAttackDelay(1000);
        getInventory().add(new FatSlugCorpus(new Point()));
        int witchAxe = random.nextInt(2);
        switch (witchAxe){
            case 0:
                getInventory().add(new GoldenAxe(new Point()));
                break;
            case 1:
                getInventory().add(new SimpleAxe(new Point()));
                break;
        }
    }
}
