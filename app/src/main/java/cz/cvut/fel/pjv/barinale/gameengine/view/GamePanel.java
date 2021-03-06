package cz.cvut.fel.pjv.barinale.gameengine.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cz.cvut.fel.pjv.barinale.gameengine.Activities.Menu;
import cz.cvut.fel.pjv.barinale.gameengine.world.WorldCreator;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Enemies.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.MainThread;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;



public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public static Resources resources = null;
    public static int SCREEN_WIDTH = 1080;
    public static int SCREEN_HEIGHT = 1920;

    private Context context;
    private MainThread thread;
    private Rect r = new Rect();

    private Point userPoint, background_point;
    private boolean screen_moving = false;
    private boolean game_over = false;
    private boolean won = false;
    private boolean pause = false;
    private long game_start_time;
    private long game_over_time;
    private long startClickTime;
    private long double_click_time = 0;
    private boolean click = false;

    private static final int REACTIONTIME = 180;

    /**
     *
     * @param pause
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     *
     * @param context
     */
    public GamePanel(Context context){
        super(context);
        this.context = context;
        SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        GamePanel.resources = getResources();
        resources = getResources();
        reset();
        setFocusable(true);
    }

    /**
     * start a new game or load a saved state
     */
    public void reset(){
        if (Menu.loadFromFile){
            Utils.loadGame(context, null);
            Menu.loadFromFile = false;
        }
        else {
            WorldCreator.createRandomMap("black_land", false);
            //Utils.loadGame(context, "black_land.txt");
        }
        userPoint = new Point(WorldCreator.player.getMapCoordinates().x + WorldCreator.background.getCoordinates().x,
                WorldCreator.player.getMapCoordinates().y + WorldCreator.background.getCoordinates().y);
        game_start_time = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        if (!thread.isAlive()) {
            thread.setRunning(true);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!pause && !game_over && !won) {
                    userPoint.set((int) event.getX(), (int) event.getY());
                    startClickTime = System.currentTimeMillis();
                    screen_moving = (click && System.currentTimeMillis() - double_click_time < REACTIONTIME);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!pause && !game_over && !won){
                    userPoint.set((int)event.getX(),(int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                click = (System.currentTimeMillis() - startClickTime < REACTIONTIME);
                if (click) double_click_time = System.currentTimeMillis();
                screen_moving = false;
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }

    /**
     * call update of all game objects
     */
    public void update(){
        if (!pause && !game_over && !won){
            background_point = (!screen_moving) ? WorldCreator.background.getCoordinates() : WorldCreator.background.update(userPoint);
            Point userMapPoint = WorldCreator.background.getUserPointCoordinates(userPoint);
            if (click){
                click = !WorldCreator.player.action(userMapPoint);
            }
            for (Entity entity: WorldCreator.entities){
                if (entity instanceof Enemy){
                    entity.update(WorldCreator.player.getMapCoordinates(), background_point);
                }
                else {
                    entity.update(userMapPoint, background_point);
                }
            }
            if (WorldCreator.player.isDead()){
                game_over = true;
            }
            WorldCreator.removeDeadEntities();
            won = Utils.checkWinCondition();
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        WorldCreator.background.draw(canvas);
        for (Entity entity: WorldCreator.entities){
            entity.draw(canvas);
        }
        for (Entity entity: WorldCreator.entities){
            entity.drawHealth(canvas);
        }
        if (game_over){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"GAME OVER!");
        }
        if (won){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"YOU WON!");
        }
    }

    private void draw_text(Canvas canvas, Paint paint, String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int c_height = r.height();
        int c_widht = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = c_widht / 2f - r.width() / 2f - r.left;
        float y = c_height / 2f - r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
