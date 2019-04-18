package cz.cvut.fel.pjv.barinale.gameengine.view;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Inventory extends View{

    private static Point userPoint;
    private static long startClickTime;
    private static long startDoubleClickTime;
    private static boolean click;
    private static boolean doubleClick;


    public Inventory(Context context) {
        super(context);
        userPoint = new Point();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                    userPoint.set((int) event.getX(), (int) event.getY());
                    startClickTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                click = (System.currentTimeMillis() - startClickTime < Constants.REACTIONTIME);
                break;
        }
        return true;
    }


}
