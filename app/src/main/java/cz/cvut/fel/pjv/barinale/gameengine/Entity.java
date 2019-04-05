package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Entity implements GameObject{

    private Rect borders;
    private Bitmap image;
    private Point entity_point;

    public Rect getBorders() {
        return borders;
    }

    public Bitmap getImage() {
        return image;
    }

    public Entity(Bitmap image){
        this.image = image;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, entity_point.x - image.getWidth()/2, entity_point.y - image.getHeight()/2, null);
    }

    @Override
    public void update() {
    }
}
