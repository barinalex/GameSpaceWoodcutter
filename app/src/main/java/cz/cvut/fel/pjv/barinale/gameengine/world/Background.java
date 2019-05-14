package cz.cvut.fel.pjv.barinale.gameengine.world;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class Background{
    private int imageId;
    private String location;
    private Bitmap image;
    private Point coordinates;
    private Size mapSize;

    private static final int BACKGROUND_SPEED = 25;

    /**
     *
     * @return
     */
    public Size getMapSize() {
        return mapSize;
    }

    /**
     *
     * @param mapSize
     */
    public void setMapSize(Size mapSize) {
        this.mapSize = mapSize;
    }

    /**
     *
     * @return
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /**
     *
     * @param location
     */
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
        mapSize = new Size();
        if (GamePanel.resources != null) {
            image = BitmapFactory.decodeResource(GamePanel.resources, imageId);
            mapSize.setWidth(image.getWidth());
            mapSize.setHeight(image.getHeight());
        }
        else {
            mapSize.setWidth(1080);
            mapSize.setHeight(1920);
        }
        coordinates = new Point(0, 0);
    }

    /**
     *
     * @param user_point
     * @return new point of moved background
     */
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

    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, coordinates.x, coordinates.y, null);
    }

    /**
     *
     * @param userPoint
     * @return point of the user tap on the map
     */
    public Point getUserPointCoordinates(Point userPoint){
        return new Point(userPoint.x - coordinates.x, userPoint.y - coordinates.y);
    }
}
