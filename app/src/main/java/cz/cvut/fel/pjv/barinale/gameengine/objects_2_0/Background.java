package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class Background{
    private int imageId;
    private String location;
    private Bitmap image;
    private Point coordinates;

    private static final int BACKGROUND_SPEED = 25;

    public Bitmap getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Background(String location){
        this.location = location;
        switch (location) {
            case "black_land":
                imageId = R.drawable.black_land;
                break;
            case "cherry_land":
                imageId = R.drawable.cherry_land;
                break;
            case "yellow_land":
                imageId = R.drawable.yellow_land;
                break;
            case "earth_land":
                imageId = R.drawable.earth_land;
                break;
        }
        image = BitmapFactory.decodeResource(GamePanel.resources, imageId);
        coordinates = new Point(0, 0);
    }

    public Point update(Point user_point){
        int border_x, border_y;
        int x, y;
        x = ((user_point.x - GamePanel.SCREEN_WIDTH/2) < 0) ? coordinates.x + BACKGROUND_SPEED: coordinates.x - BACKGROUND_SPEED;
        y = ((user_point.y - GamePanel.SCREEN_HEIGHT/2) < 0) ? coordinates.y + BACKGROUND_SPEED: coordinates.y - BACKGROUND_SPEED;
        border_x = (GamePanel.SCREEN_WIDTH -(image.getWidth()));
        border_y = (GamePanel.SCREEN_HEIGHT - (image.getHeight()));
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
