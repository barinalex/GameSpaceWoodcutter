package cz.cvut.fel.pjv.barinale.gameengine;

import android.content.Context;
import android.graphics.BitmapFactory;
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
    private OldPlayer oldPlayer;
    private Player player;
    private Point user_point, background_point;
    private Button pause;
    private Button use;
    private ArrayList<StaticObject> map_objects;
    private ArrayList<Protagonist> protagonists;
    private ArrayList<GameObject> gameObjects;

    private boolean moving_player = false;
    private boolean screen_moving = false;
    private boolean press_button = false;
    private int press_button_counter = 0;
    private boolean game_over = false;
    private boolean won = false;
    private long game_start_time;
    private long game_over_time;
    private long double_click_time = 0;
    private boolean click = false;
    private boolean act = false;
    private long action_down_time;

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
        user_point = new Point(Constants.SCREEN_WIDTH / 2,Constants.SCREEN_HEIGHT / 2);
        background = new Background(ImageArchive.images.get(Constants.BACKGROUND).get(0));
        player = GameObjectManager.addPlayer();
        GameObjectManager.addObjects(1,2,1,1);
        gameObjects = (ArrayList<GameObject>) (GameObjectManager.gameObjects).clone();
        CollisionDetecter.gameObjects = gameObjects;
        moving_player = false;
        thread.setLevel(2);
        thread.setTotal_time(0);
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
                action_down_time = System.currentTimeMillis();
                if(!game_over && !won) {
                    press_button = true;
                    press_button_counter = (press_button_counter + 1) % 2;
                    user_point.set((int) event.getX(), (int) event.getY());
                    if (click && System.currentTimeMillis() - double_click_time < 180){
                        screen_moving = true;
                    }
                    if (!screen_moving) moving_player = true;
                    double_click_time = System.currentTimeMillis();
                    click = false;
                    act = true;
                }
                if ((game_over || won) && System.currentTimeMillis() - game_over_time >=1000){
                    reset();
                    game_over = false;
                    won = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (System.currentTimeMillis() - action_down_time > 180)
                    act = false;
                if (!game_over && !won && (moving_player || screen_moving))
                    user_point.set((int)event.getX(),(int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                moving_player = false;
                screen_moving = false;
                press_button = false;
                click = true;
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        if (Constants.CONFIG_CHANGED){
            background.update(user_point);
            Constants.CONFIG_CHANGED = false;
        }
        if (!game_over && !won) {
            if (!pause.isActivated()) {
                if (!screen_moving)
                    background_point = background.getCoordinate();
                else
                    background_point = background.update(user_point);
                for (GameObject gameObject : gameObjects) {
                    if (gameObject.getType() == 4){
                        gameObject.update(player.getMapCoordinates(), background_point);
                    }
                    else {
                        gameObject.update(background.getUserPointCoordinates(user_point), background_point);
                    }
                }
            }
        }
        /*
        if (!game_over && !won) {
            if (map_objects.isEmpty()) {
                won = true;
                game_over_time = System.currentTimeMillis();
                return;
            }
            use.update(user_point);
            pause.update(user_point, press_button);
            for (StaticObject staticObject : map_objects){
                if (act && click && staticObject.able_to_act(oldPlayer)){
                    staticObject.update_strenght(user_point, oldPlayer);
                    act = false;
                    if (staticObject.isDestroid()){
                        map_objects.remove(staticObject);
                    }
                }
            }
            if (!pause.isActivated()) {
                oldPlayer.setMoving_player(moving_player);
                Point player_map_pos = background.getMapPosition(oldPlayer);
                for (Protagonist protagonist: protagonists){
                    protagonist.setScreenPoint(background.getMapPosition(protagonist));
                }
                background_point = background.getCoordinate();
                if (moving_player) {
                    oldPlayer.update(user_point, map_objects);
                }
                if (!screen_moving){
                    for (Protagonist protagonist: protagonists){
                        protagonist.update(oldPlayer, map_objects, protagonists);
                        if (protagonist.player_catched(oldPlayer)){
                            game_over = true;
                            game_over_time = System.currentTimeMillis();
                            break;
                        }
                    }
                }
                if (screen_moving) {
                    background_point = background.update(user_point);
                    for (StaticObject staticObject : map_objects){
                        staticObject.update(background_point);
                    }
                    for (Protagonist protagonist: protagonists) {
                        protagonist.update(background_point);
                    }
                    oldPlayer.update(background_point, player_map_pos);
                }
            }
            else {
                oldPlayer.setMoving_player(false);
            }
        }
        */
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        background.draw(canvas);
        for (GameObject gameObject: gameObjects){
            gameObject.draw(canvas);
        }
        /*
        for (StaticObject staticObject : map_objects){
            staticObject.draw(canvas);
            if (System.currentTimeMillis() - staticObject.getAct_time() < 2000){
                Paint paint = new Paint();
                paint.setTextSize(100);
                paint.setColor(Color.BLACK);
                draw_text(canvas,paint,"strenght: " + staticObject.getStrength());
            }
        }
        for (Protagonist protagonist: protagonists) {
            protagonist.draw(canvas);
        }
        oldPlayer.draw(canvas);
        pause.draw(canvas);
        use.draw(canvas);
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
        */
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
