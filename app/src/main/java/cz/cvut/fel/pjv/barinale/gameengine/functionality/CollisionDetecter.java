package cz.cvut.fel.pjv.barinale.gameengine.functionality;

import android.graphics.Rect;

import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Creatures.Creature;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Entity;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Teleport;

public class CollisionDetecter {

    public static boolean isCollide(Entity entity){
        if (entity.getBody() != null) {
            for (Entity e : EntityManager.entities) {
                if (e != entity && !(e instanceof Item) && !(e instanceof Teleport) && Rect.intersects(e.getBody(), entity.getBody())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCollide(Creature creature, Entity entity){
        return !(entity instanceof Item) && !(entity instanceof Teleport) && Rect.intersects(creature.getBody(), entity.getActiveZone());
    }
/*
    private boolean chech_collision_bitmap(Point new_point, ArrayList<StaticObject> map_objects){
        Rect rect = new Rect(new_point.x - main_image.getWidth() / 4, new_point.y - main_image.getHeight() / 4,
                new_point.x + main_image.getWidth() / 4, new_point.y + main_image.getHeight() / 4);
        Point screen_pos;
        Bitmap image;
        for (StaticObject mapObject : map_objects){
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
    }*/
}
