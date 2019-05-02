package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Background{
    private Bitmap image;
    private Point coordinate;

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Background(Bitmap res){
        image = res;
        coordinate = new Point(0, 0);
    }

    public Point update(Point user_point){
        int border_x, border_y;
        int x, y;
        x = ((user_point.x - Constants.SCREEN_WIDTH/2) < 0) ? coordinate.x + Constants.BACKGROUND_SPEED: coordinate.x - Constants.BACKGROUND_SPEED;
        y = ((user_point.y - Constants.SCREEN_HEIGHT/2) < 0) ? coordinate.y + Constants.BACKGROUND_SPEED: coordinate.y - Constants.BACKGROUND_SPEED;
        border_x = (Constants.SCREEN_WIDTH -(image.getWidth()));
        border_y = (Constants.SCREEN_HEIGHT - (image.getHeight()));
        x = (x > 0) ? 0 : x;
        x = (x < border_x) ? border_x : x;
        y = (y > 0) ? 0 : y;
        y = (y < border_y) ? border_y : y;
        coordinate.set(x, y);
        return new Point(coordinate.x, coordinate.y);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, coordinate.x, coordinate.y, null);
    }

    public Point getUserPointCoordinates(Point userPoint){
        return new Point(userPoint.x - coordinate.x, userPoint.y - coordinate.y);
    }
}
