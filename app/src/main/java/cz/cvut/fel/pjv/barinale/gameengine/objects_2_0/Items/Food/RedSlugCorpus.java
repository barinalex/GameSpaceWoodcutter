package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class RedSlugCorpus extends Corpus {
    /**
     *
     * @param mapCoordinates
     */
    public RedSlugCorpus(Point mapCoordinates) {
        super(mapCoordinates);
        setSize(new Size(32, 32));
        if (GamePanel.resources != null) {
            setMainImageId(R.drawable.red_slug_dead);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        }
        setBody();
        setActiveZone();
        setHealth(new Characteristic(10));
    }
}
