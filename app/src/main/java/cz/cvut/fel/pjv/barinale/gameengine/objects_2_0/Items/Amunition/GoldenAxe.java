package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Amunition;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Characteristic;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class GoldenAxe extends Axe{
    public GoldenAxe(Point mapCoordinates) {
        super(mapCoordinates);
        setMainImageId(R.drawable.golden_axe);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        setBody();
        setActiveZone();
        setStrength(new Characteristic(4,4));
    }
}
