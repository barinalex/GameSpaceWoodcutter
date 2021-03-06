package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class GoldenAxe extends Axe{
    /**
     *
     * @param mapCoordinates
     */
    public GoldenAxe(Point mapCoordinates) {
        super(mapCoordinates);
        setSize(new Size(32, 32));
        if (GamePanel.resources != null) {
            setMainImageId(R.drawable.golden_axe);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        }
        setBody();
        setActiveZone();
        setStrength(new Characteristic(4,4));
    }
}
