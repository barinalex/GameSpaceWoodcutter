package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Point;

import java.util.ArrayList;

public class Player implements GameObject{

    private Rect body;
    private ArrayList<MapObject> inventory;
    private Bitmap main_image;
    private Bitmap move_image;
    private Point player_point;
    private boolean collisioned = false;
    private long step_time = 0;
    private boolean step = false;
    private boolean moving_player = false;

    public Point getPlayer_point() {
        return player_point;
    }

    public Rect getBody() {
        return body;
    }

    public Bitmap getMain_image() {
        return main_image;
    }

    public void setMoving_player(boolean moving_player) {
        this.moving_player = moving_player;
    }

    public Player(Bitmap main_image, Bitmap move_image){
        this.main_image = main_image;
        this.move_image = move_image;
        inventory = new ArrayList<>();
        player_point = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);
        body = new Rect();
        set_body();
    }

    @Override
    public void draw(Canvas canvas) {
        if (moving_player && System.currentTimeMillis() - step_time > 100){
            step = (step) ? false: true;
            step_time = System.currentTimeMillis();
        }
        if (!step)
            canvas.drawBitmap(main_image, player_point.x - main_image.getWidth()/2, player_point.y - main_image.getHeight()/2, null);
        else
            canvas.drawBitmap(move_image, player_point.x - main_image.getWidth()/2, player_point.y - main_image.getHeight()/2, null);
    }

    @Override
    public void update() {
        player_point.set(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2);
        set_body();
    }

    public void update(Point background_position ,Point map_position){
        player_point.set(background_position.x + map_position.x,background_position.y + map_position.y);
        set_body();
    }

    public boolean update(Point user_point, ArrayList<MapObject> mapObjects){
        for (int i = 1; i <= 5; i++) {
            Point direction = get_direction(user_point,Constants.PLAYER_SPEED);
            pick_up_to_inventory(mapObjects);
            if (!chech_collision(new Point(player_point.x + direction.x, player_point.y + direction.y), mapObjects)) {
                collisioned = false;
                player_point.x += direction.x;
                player_point.y += direction.y;
                set_body();
                return true;
            }
        }
        return false;
    }

    private boolean chech_collision(Point new_point, ArrayList<MapObject> map_objects){
        Rect rect = new Rect(new_point.x - main_image.getWidth() / 4, new_point.y - main_image.getHeight() / 4,
                new_point.x + main_image.getWidth() / 4, new_point.y + main_image.getHeight() / 4);
        for (MapObject mapObject : map_objects){
            if (mapObject.getForbidden_zone() != null && Rect.intersects(rect, mapObject.getForbidden_zone())) {
                collisioned = true;
                return true;
            }
        }
        return false;
    }


    private boolean chech_collision_bitmap(Point new_point, ArrayList<MapObject> map_objects){
        Rect rect = new Rect(new_point.x - main_image.getWidth() / 4, new_point.y - main_image.getHeight() / 4,
                new_point.x + main_image.getWidth() / 4, new_point.y + main_image.getHeight() / 4);
        Point screen_pos;
        Bitmap image;
        for (MapObject mapObject : map_objects){
            screen_pos = mapObject.getScreen_position();
            image = mapObject.getImage();
            int pixel;
            Rect bounds;
            //if (Rect.intersects(rect, mapObject.getForbidden_zone())) {
                //bounds = getCollisionBounds(rect, mapObject.getForbidden_zone());
                if ((pixel = image.getPixel(new_point.x - screen_pos.x, new_point.y - screen_pos.y)) != Color.TRANSPARENT) {
                    collisioned = true;
                    return true;
                }
            //}
        }
        return false;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private void set_body(){
        body.set(player_point.x - main_image.getWidth() / 4, player_point.y - main_image.getHeight() / 2,
                player_point.x + main_image.getWidth() / 4, player_point.y + main_image.getHeight() / 4);
    }

    public void pick_up_to_inventory(ArrayList<MapObject> map_objects){
        for (int i = 0; i < map_objects.size(); i++) {
            if (map_objects.get(i).getType() == 5 && Rect.intersects(body, map_objects.get(i).getActive_zone())) {
                inventory.add(map_objects.get(i));
                map_objects.remove(map_objects.remove(i--));
            }
        }
    }

    public MapObject check_activity(ArrayList<MapObject> map_objects){
        for (MapObject mapObject : map_objects){
            if (Rect.intersects(body, mapObject.getActive_zone())) return mapObject;
        }
        return null;
    }

    private Point get_direction(Point user_point, int speed){
        double v_rate, a = speed;// e = a*v chceme vector jedotkovy ve stejnem smeru
        int x = user_point.x - (player_point.x);
        int y = user_point.y - (player_point.y);
        v_rate = Math.sqrt((Math.pow((double) x, 2) + Math.pow((double) y, 2)));
        if (v_rate != 0) a /= v_rate;
        System.out.println(a);
        Point direction = new Point();
        direction.x = (int)(x * a);
        direction.y = (int)(y * a);
        return direction;
    }
}
