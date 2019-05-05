package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public class Background{
    private int imageId;
    private String location;
    private Bitmap image;
    private Point coordinates;

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Background(boolean teleportated){
        if (teleportated)
            imageId = R.drawable.desert_mntn2_n;
        else
            imageId = R.drawable.desert_mntn2_s;
        image = BitmapFactory.decodeResource(Constants.resources, imageId);
        coordinates = new Point(0, 0);
    }

    public Point update(Point user_point){
        int border_x, border_y;
        int x, y;
        x = ((user_point.x - Constants.SCREEN_WIDTH/2) < 0) ? coordinates.x + Constants.BACKGROUND_SPEED: coordinates.x - Constants.BACKGROUND_SPEED;
        y = ((user_point.y - Constants.SCREEN_HEIGHT/2) < 0) ? coordinates.y + Constants.BACKGROUND_SPEED: coordinates.y - Constants.BACKGROUND_SPEED;
        border_x = (Constants.SCREEN_WIDTH -(image.getWidth()));
        border_y = (Constants.SCREEN_HEIGHT - (image.getHeight()));
        x = (x > 0) ? 0 : x;
        x = (x < border_x) ? border_x : x;
        y = (y > 0) ? 0 : y;
        y = (y < border_y) ? border_y : y;
        coordinates.set(x, y);
        return new Point(coordinates.x, coordinates.y);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, coordinates.x, coordinates.y, null);
    }

    public Point getUserPointCoordinates(Point userPoint){
        return new Point(userPoint.x - coordinates.x, userPoint.y - coordinates.y);
    }
}
