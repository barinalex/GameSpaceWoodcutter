package cz.cvut.fel.pjv.barinale.gameengine.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class ImageArchive {
    public static ArrayList<ArrayList<Bitmap>> images;
    public static int[] imagesId;
    public static Point map_size;
    public static void read_images(){
        images = new ArrayList<>();
        imagesId = new int[7];
        for (int i = 0; i < 7; i++) {
            images.add(new ArrayList<Bitmap>());
        }
        images.get(Constants.BACKGROUND).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.seamless));
        map_size = new Point(images.get(Constants.BACKGROUND).get(0).getWidth(), images.get(Constants.BACKGROUND).get(0).getHeight());
        images.get(Constants.CABIN).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.teleport));
        imagesId[1] = R.drawable.log_cabin;
        images.get(Constants.BLUETREE).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.blue_tree_l));
        imagesId[2] = R.drawable.blue_tree_l;
        images.get(Constants.GREENTREE).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.green_tree_r));
        imagesId[3] = R.drawable.green_tree_r;
        images.get(Constants.ENEMY).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.protagonist));
        imagesId[4] = R.drawable.protagonist;
        images.get(Constants.ITEM).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.axe));
        imagesId[5] = R.drawable.axe;
        images.get(Constants.PLAYER).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.redhead));
        imagesId[6] = R.drawable.redhead;
        images.get(Constants.PLAYER).add(BitmapFactory.decodeResource(Constants.resources, R.drawable.redhead_1));
    }
}
