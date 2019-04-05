package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class MapObject implements GameObject{

    private String name;
    private Rect forbidden_zone, active_zone;
    private Bitmap image;
    private Point screen_position;
    private Point map_pos;
    private int type;
    private int act_counter = 0;
    private boolean destroid = false;
    private long act_time;
    private int coef = 7;

    public Rect getForbidden_zone() {
        return forbidden_zone;
    }

    public Point getScreen_position() {
        return screen_position;
    }

    public int getType() {
        return type;
    }

    public Bitmap getImage() {
        return image;
    }

    public Rect getActive_zone() {
        return active_zone;
    }

    public String getName() {
        return name;
    }

    public int getAct_counter() {
        return act_counter;
    }

    public boolean isDestroid() {
        return destroid;
    }

    public MapObject(Bitmap image, Point map_pos, String name, int type){
        this.name = name;
        this.type = type;
        this.map_pos = map_pos;
        this.image = image;
        screen_position = new Point(map_pos.x, map_pos.y);
        forbidden_zone = new Rect();
        active_zone = new Rect();
        update(new Point(0,0));
    }

    @Override
    public void update(){
    }

    public void update(Point point){
            screen_position.set(point.x + map_pos.x, point.y + map_pos.y);
            if (type == 1) {
                forbidden_zone.set(screen_position.x + image.getWidth() / coef, screen_position.y + image.getHeight() / coef,
                        screen_position.x + image.getWidth() - image.getWidth() / coef, screen_position.y + image.getHeight() - image.getWidth() / coef);
            }
            if (type == 2 || type == 3){
                forbidden_zone.set(screen_position.x + image.getWidth() / 3, screen_position.y + image.getHeight() * 6 / 8,
                        screen_position.x + image.getWidth() - image.getWidth() / 3, screen_position.y + image.getHeight() * 7 / 8);
            }
            if (type == 5){
                forbidden_zone = null;
            }
            active_zone.set(screen_position.x - image.getWidth() / coef, screen_position.y - image.getHeight() / coef,
                    screen_position.x + image.getWidth() + image.getWidth() / coef, screen_position.y + image.getHeight() + image.getWidth() / coef);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, screen_position.x, screen_position.y, null);
    }

    public boolean able_to_act(Player player){
        return active_zone != null && Rect.intersects(active_zone, player.getBody());
    }

    public void act(Point user_point){
        if (forbidden_zone.contains(user_point.x, user_point.y)) {
            if (act_counter == 0) {
                act_time = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - act_time < 1000) {
                act_time = System.currentTimeMillis();
                act_counter++;
            } else {
                act_counter = 0;
            }
        }
        if (act_counter > 7){
            destroid = true;
        }
    }
}
