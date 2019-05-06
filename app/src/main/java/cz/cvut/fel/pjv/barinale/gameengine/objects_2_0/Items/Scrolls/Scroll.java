package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;

public abstract class Scroll extends Item {
    private String text;
    private Bitmap scrollImage;
    private boolean openScroll;

    public boolean isOpenScroll() {
        return openScroll;
    }

    public void openScroll() {
        openScroll = true;
        getScreenCoordinates().set(Constants.SCREEN_WIDTH / 2  - scrollImage.getWidth()/2, 50);
        EntityManager.entities.add(this);
    }

    public void closeScroll(){
        openScroll = false;
        EntityManager.entities.remove(this);
    }

    public void setText(String text) {
        this.text = text;
        openScroll = false;
    }

    public Scroll(Point mapCoordinates) {
        super(mapCoordinates);
    }

    public void initializeScroll(int imageId, int scrollImageId){
        setMainImageId(imageId);
        setMainImage(BitmapFactory.decodeResource(Constants.resources, getMainImageId()));
        scrollImage = (BitmapFactory.decodeResource(Constants.resources, scrollImageId));
        setBody();
        setActiveZone();
    }

    @Override
    public void draw(Canvas canvas) {
        if (openScroll){
            canvas.drawBitmap(scrollImage,
                    getScreenCoordinates().x,
                    getScreenCoordinates().y, null);
        }
        else {
            super.draw(canvas);
        }
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        if (!openScroll) {
            super.update(userPoint, mapPosition);
        }
    }
}
