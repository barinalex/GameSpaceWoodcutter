package cz.cvut.fel.pjv.barinale.gameengine.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cz.cvut.fel.pjv.barinale.gameengine.objects.Background;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.CollisionDetecter;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Enemy;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.FightManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects.GameObject;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.ImageArchive;
import cz.cvut.fel.pjv.barinale.gameengine.MainThread;
import cz.cvut.fel.pjv.barinale.gameengine.objects.Player;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background background;
    private Rect r = new Rect();
    private Player player;
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

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public GamePanel(Context context){
        super(context);
        Constants.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Constants.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        Constants.resources = getResources();
        ImageArchive.read_images();
        reset();
        setFocusable(true);
    }

    public void reset(){
        userPoint = new Point(Constants.SCREEN_WIDTH / 2,Constants.SCREEN_HEIGHT / 2);
        background = new Background(ImageArchive.images.get(Constants.BACKGROUND).get(0));
        GameObjectManager.addObjects(2,15,1,3);
        player = GameObjectManager.player;
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
                    screen_moving = (click && System.currentTimeMillis() - double_click_time < Constants.REACTIONTIME);
                }
                if ((game_over || won) && System.currentTimeMillis() - game_over_time >= 1000){
                    reset();
                    game_over = false;
                    won = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!pause && !game_over && !won){
                    userPoint.set((int)event.getX(),(int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                click = (System.currentTimeMillis() - startClickTime < Constants.REACTIONTIME);
                if (click) double_click_time = System.currentTimeMillis();
                screen_moving = false;
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        if (Constants.CONFIG_CHANGED){
            background.update(userPoint);
            Constants.CONFIG_CHANGED = false;
        }
        if (!won && Utils.checkWinCondition(GameObjectManager.gameObjects)){
            game_over_time = System.currentTimeMillis();
            won = true;
        }
        if (!game_over && Utils.checkGameOver()){
            game_over_time = System.currentTimeMillis();
            game_over = true;
        }
        if (!game_over && !won) {
            if (!pause) {
                background_point = (!screen_moving) ? background.getCoordinate() : background.update(userPoint);
                player.pick_up_to_inventory(GameObjectManager.gameObjects);
                for (GameObject gameObject : GameObjectManager.gameObjects) {
                    if (gameObject.getType() == Constants.ENEMY){
                        gameObject.update(player.getMapCoordinates(), background_point);
                        FightManager.attackPlayer((Enemy) gameObject);
                    }
                    else {
                        gameObject.update(background.getUserPointCoordinates(userPoint), background_point);
                    }
                    if (click && gameObject != player && CollisionDetecter.playerInActiveZone(player, gameObject) &&
                            FightManager.attackIsSuccess(gameObject, player, background.getUserPointCoordinates(userPoint))){
                            click = false;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        background.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        draw_text(canvas, paint, "health: " + player.getCharacteristics()[Constants.HEALTH]);
        for (GameObject gameObject: GameObjectManager.gameObjects){
            gameObject.draw(canvas);
        }
        if (game_over){
            paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"game over!");
        }
        if (won){
            paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"You won! Your time: " + (game_over_time - game_start_time) / 1000 + " s");
        }
        for (GameObject gameObject : GameObjectManager.gameObjects) {
            if (gameObject != player && CollisionDetecter.playerInActiveZone(player, gameObject)) {
                gameObject.draw(canvas);
                paint = new Paint();
                paint.setTextSize(50);
                paint.setColor(Color.BLACK);
                draw_text(canvas, paint, "health: " + gameObject.getCharacteristics()[Constants.HEALTH]);
            }
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
