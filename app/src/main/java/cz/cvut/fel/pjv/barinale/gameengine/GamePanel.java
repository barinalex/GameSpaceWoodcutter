package cz.cvut.fel.pjv.barinale.gameengine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background background;
    private Rect r = new Rect();
    private Player player;
    private Point userPoint, background_point;
    private Button pause;
    private Button use;

    private boolean moving_player = false;
    private boolean screen_moving = false;
    private boolean game_over = false;
    private boolean won = false;
    private long game_start_time;
    private long game_over_time;
    private long startClickTime;
    private long double_click_time = 0;
    private boolean click = false;

    public GamePanel(Context context){
        super(context);
        Constants.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Constants.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        Constants.resources = getResources();
        ImageArchive.read_images();
        pause = new Button(new Rect(200, Constants.SCREEN_HEIGHT - 200,
                300, Constants.SCREEN_HEIGHT - 100), Color.BLACK);
        use = new Button(new Rect(400, Constants.SCREEN_HEIGHT - 200,
                500, Constants.SCREEN_HEIGHT - 100), Color.BLUE);
        reset();
        setFocusable(true);
    }

    public void reset(){
        userPoint = new Point(Constants.SCREEN_WIDTH / 2,Constants.SCREEN_HEIGHT / 2);
        background = new Background(ImageArchive.images.get(Constants.BACKGROUND).get(0));
        player = GameObjectManager.addPlayer();
        GameObjectManager.addObjects(2,10,2,2);
        game_start_time = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        while (true){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!game_over && !won) {
                    userPoint.set((int) event.getX(), (int) event.getY());
                    startClickTime = System.currentTimeMillis();
                    screen_moving = (click && System.currentTimeMillis() - double_click_time < Constants.REACTIONTIME);
                    moving_player = !screen_moving;
                }
                if ((game_over || won) && System.currentTimeMillis() - game_over_time >= 1000){
                    reset();
                    game_over = false;
                    won = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!game_over && !won && (moving_player || screen_moving)){
                    userPoint.set((int)event.getX(),(int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - startClickTime < Constants.REACTIONTIME){
                    click = true;
                    double_click_time = System.currentTimeMillis();
                }
                else {
                    click = false;
                }
                moving_player = false;
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
        if (!game_over && !won) {
            if (!pause.isActivated()) {
                background_point = (!screen_moving) ? background.getCoordinate() : background.update(userPoint);
                player.pick_up_to_inventory(GameObjectManager.gameObjects);
                for (GameObject gameObject : GameObjectManager.gameObjects) {
                    if (gameObject.getType() == Constants.ENEMY){
                        gameObject.update(player.getMapCoordinates(), background_point);
                    }
                    else {
                        gameObject.update(background.getUserPointCoordinates(userPoint), background_point);
                    }
                    if (click && gameObject != player && CollisionDetecter.playerInActiveZone(player, gameObject)){
                        FightManager.updateHealth(gameObject, player, background.getUserPointCoordinates(userPoint));
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
        for (GameObject gameObject: GameObjectManager.gameObjects){
            gameObject.draw(canvas);
        }
        for (GameObject gameObject : GameObjectManager.gameObjects) {
            if (gameObject != player && CollisionDetecter.playerInActiveZone(player, gameObject)) {
                gameObject.draw(canvas);
                Paint paint = new Paint();
                paint.setTextSize(50);
                paint.setColor(Color.BLACK);
                draw_text(canvas, paint, "health: " + gameObject.getCharacteristics()[Constants.HEALTH]);
            }
        }

        if (pause.isActivated()){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLACK);
            draw_text(canvas,paint,"PAUSE");
        }
        if (game_over){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"game over!");
        }
        if (won){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            draw_text(canvas,paint,"You won! Your time: " + (game_over_time - game_start_time) / 1000 + " s");
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
