package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public abstract class Wood extends Item {
    /**
     *
     * @param mapCoordinates
     */
    public Wood(Point mapCoordinates) {
        super(mapCoordinates);
        setSpeed(new Characteristic(-2));
    }

    /**
     *
     * @param imageId
     */
    public void initializeWood(int imageId){

        setSize(new Size(28, 30));
        if (GamePanel.resources != null) {
            setMainImageId(imageId);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        }
        setBody();
        setActiveZone();
    }
}
