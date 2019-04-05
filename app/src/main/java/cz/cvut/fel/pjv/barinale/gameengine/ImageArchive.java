package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

public class ImageArchive {
    public static ArrayList<Bitmap> images;
    public static final int MAP = 0;
    public static final int CABIN = 1;
    public static final int BLUETREE = 2;
    public static final int GREENTREE = 3;
    public static final int PROTAGONIST = 4;
    public static Point map_size;
    public static void read_images(){
        images = new ArrayList<>();
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.seamless));
        map_size = new Point(images.get(0).getWidth(), images.get(0).getHeight());
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.log_cabin));
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.blue_tree_l));
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.green_tree_r));
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.protagonist));
        images.add(BitmapFactory.decodeResource(Constants.resources, R.drawable.green_tree_r));
    }
}
