package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Background {
    private Bitmap image;
    private Point coordinate;

    public Point getCoordinate() {
        return coordinate;
    }

    public Background(Bitmap res){
        image = res;
        coordinate = new Point();
    }

    public Point update(Point user_point){
        int border_x, border_y;
        int new_x, new_y;
        new_x = ((user_point.x - Constants.SCREEN_WIDTH/2) < 0) ? coordinate.x + Constants.BACKGROUND_SPEED: coordinate.x - Constants.BACKGROUND_SPEED;
        new_y = ((user_point.y - Constants.SCREEN_HEIGHT/2) < 0) ? coordinate.y + Constants.BACKGROUND_SPEED: coordinate.y - Constants.BACKGROUND_SPEED;
        border_x = (Constants.SCREEN_WIDTH -(image.getWidth()));
        border_y = (Constants.SCREEN_HEIGHT - (image.getHeight()));
        new_x = (new_x > 0) ? 0 : new_x;
        new_x = (new_x < border_x) ? border_x : new_x;
        new_y = (new_y > 0) ? 0 : new_y;
        new_y = (new_y < border_y) ? border_y : new_y;
        coordinate.set(new_x, new_y);
        return new Point(coordinate.x, coordinate.y);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, coordinate.x, coordinate.y, null);
    }

    public Point getMapPosition(Player player){
        return new Point(player.getPlayer_point().x - coordinate.x, player.getPlayer_point().y - coordinate.y);
    }

    public Point getMapPosition(Protagonist protagonist){
        return new Point(protagonist.getProtagonist_point().x - coordinate.x, protagonist.getProtagonist_point().y - coordinate.y);
    }}
