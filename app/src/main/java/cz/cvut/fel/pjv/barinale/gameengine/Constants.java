package cz.cvut.fel.pjv.barinale.gameengine;

import android.content.res.Resources;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Resources resources;
    public static int PLAYER_SPEED = 15;
    public static int PROTAGONIST_SPEED = 10;
    public static int BACKGROUND_SPEED = 25;
    public static int[] TYPE_HEALTH = {0,20,10,10,5,2,15};
    public static int[] TYPE_SPEED = {0,0,0,0,8,0,15};
    public static int[] TYPE_STRENGHT = {0,0,0,0,1,2,1};

    public static final int REACTIONTIME = 180;

    public static final int HEALTH = 0;
    public static final int SPEED = 1;
    public static final int STRENGHT = 2;
    public static boolean CONFIG_CHANGED = false;


    public static final int BACKGROUND = 0;
    public static final int CABIN = 1;
    public static final int BLUETREE = 2;
    public static final int GREENTREE = 3;
    public static final int ENEMY = 4;
    public static final int ITEM = 5;
    public static final int PLAYER = 6;

    public static final int MAINIMAGE = 0;
    public static final int SECONDARYIMAGE = 1;
}
