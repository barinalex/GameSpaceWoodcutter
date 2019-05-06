package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class FatSlugCorpus extends Corpus {
    public FatSlugCorpus(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.fat_slug_dead);
        setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setHealth(new Characteristic(5));
    }
}
