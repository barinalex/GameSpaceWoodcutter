package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Food.OneEyeCorpus;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class OneEye extends Enemy{
    /**
     *
     * @param mapCoordinates
     */
    public OneEye(Point mapCoordinates) {
        super(mapCoordinates);
        setSize(new Size(32, 32));
        if (GamePanel.resources != null) {
            setMainImageId(R.drawable.one_eye1);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
            setMoveImages(new ArrayList<Bitmap>());
            getMoveImages().add(getMainImage());
            getMoveImages().add(BitmapFactory.decodeResource(GamePanel.resources, R.drawable.one_eye_step1));
            getMoveImages().add(BitmapFactory.decodeResource(GamePanel.resources, R.drawable.one_eye_step2));
        }
        setBody();
        setActiveZone();
        setHealth(new Characteristic(7));
        setSpeed(new Characteristic(10));
        setProtection(new Characteristic(0));
        setStrength(new Characteristic(3, 3));
        setAttackDelay(750);
        getInventory().add(new OneEyeCorpus(new Point()));
    }
}
