package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class Protagonist implements GameObject{
    private Rect body;
    private Bitmap main_image;
    private String name;
    private Point protagonist_point;
    private Point global_map_point;

    public Rect getBody() {
        return body;
    }

    public void setGlobal_map_point(Point global_map_point) {
        this.global_map_point = global_map_point;
    }

    public Point getProtagonist_point() {
        return protagonist_point;
    }

    public Protagonist(Bitmap main_image, Point protagonist_point,  String name){
        this.name = name;
        this.main_image = main_image;
        this.protagonist_point = protagonist_point;
        body = new Rect();
        set_body();
    }

    private void set_body(){
        body.set(protagonist_point.x - main_image.getWidth() / 4, protagonist_point.y - main_image.getHeight() / 2,
                protagonist_point.x + main_image.getWidth() / 4, protagonist_point.y + main_image.getHeight() / 4);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(main_image, protagonist_point.x - main_image.getWidth()/2, protagonist_point.y - main_image.getHeight()/2, null);
    }

    @Override
    public void update() {
    }

    public void update(Point background_position){
        protagonist_point.set(background_position.x + global_map_point.x,background_position.y + global_map_point.y);
        set_body();
    }

    public void update(Player player, ArrayList<MapObject> mapObjects, ArrayList<Protagonist> enemies) {
        Point direction = get_direction(player.getPlayer_point());
        if (!chech_collision(new Point(protagonist_point.x + direction.x, protagonist_point.y + direction.y), mapObjects, enemies)) {
            protagonist_point.x += direction.x;
            protagonist_point.y += direction.y;
            set_body();
        }
    }

    public boolean player_cathced(Player player){
        return Rect.intersects(player.getBody(), body);
    }

    private boolean chech_collision(Point new_point, ArrayList<MapObject> map_objects, ArrayList<Protagonist> enemies){
        Rect rect = new Rect(new_point.x - main_image.getWidth() / 4, new_point.y - main_image.getHeight() / 4,
                new_point.x + main_image.getWidth() / 4, new_point.y + main_image.getHeight() / 4);
        for (Protagonist protagonist : enemies){
            if (protagonist != this && Rect.intersects(rect, protagonist.getBody())) {
                return true;
            }
        }
        for (MapObject mapObject : map_objects){
            if (mapObject.getForbidden_zone() != null && Rect.intersects(rect, mapObject.getForbidden_zone())) {
                return true;
            }
        }
        return false;
    }
    private Point get_direction(Point user_point){
        double v_rate, a;// e = a*v chceme vector jedotkovy ve stejnem smeru
        a = Constants.PROTAGONIST_SPEED;
        int x = user_point.x - (protagonist_point.x);
        int y = user_point.y - (protagonist_point.y);
        v_rate = Math.sqrt((Math.pow((double) x, 2) + Math.pow((double) y, 2)));
        if (v_rate != 0) a /= v_rate;
        System.out.println(a);
        Point direction = new Point();
        direction.x = (int)(x * a);
        direction.y = (int)(y * a);
        return direction;
    }
}
