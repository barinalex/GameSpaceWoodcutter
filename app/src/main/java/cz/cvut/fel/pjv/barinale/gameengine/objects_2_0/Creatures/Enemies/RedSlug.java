package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.RedSlugCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Runes.RedRune;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class RedSlug extends Enemy{
    public RedSlug(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.red_slug);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setMoveImages(new ArrayList<Bitmap>());
        getMoveImages().add(getMainImage());
        getMoveImages().add(BitmapFactory.decodeResource(Constants.resources, R.drawable.red_slug_step));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(20));
        setSpeed(new Characteristic(10));
        setProtection(new Characteristic(3));
        setStrength(new Characteristic(5));
        setAttackDelay(600);
        getInventory().add(new RedRune(new Point()));
        getInventory().add(new RedSlugCorpus(new Point()));
    }
}
