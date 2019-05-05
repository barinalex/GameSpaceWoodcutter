package cz.cvut.fel.pjv.barinale.gameengine.utils;

import android.content.res.Resources;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Resources resources;
    public static int BACKGROUND_SPEED = 25;

    public static final int REACTIONTIME = 180;
    public static boolean CONFIG_CHANGED = false;


    public static final int BACKGROUND = 0;
    public static final int CABIN = 1;
    public static final int BLUETREE = 2;
    public static final int GREENTREE = 3;
    public static final int ENEMY = 4;
    public static final int ITEM = 5;
    public static final int PLAYER = 6;

    public static String savedGameFileName = "save.txt";
    public static String mapFileName = "level_1.txt";

    public static boolean loadFromFile = false;
    public static boolean randomMap = false;
}
