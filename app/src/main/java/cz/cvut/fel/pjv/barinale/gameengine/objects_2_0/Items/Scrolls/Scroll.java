package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Scrolls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.cvut.fel.pjv.barinale.gameengine.world.WorldCreator;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public abstract class Scroll extends Item {
    private String text;
    private Bitmap scrollImage;
    private boolean openScroll;

    public boolean isOpenScroll() {
        return openScroll;
    }

    /**
     *
     */
    public void openScroll() {
        openScroll = true;
        getScreenCoordinates().set(GamePanel.SCREEN_WIDTH / 2  - scrollImage.getWidth()/2, 50);
        WorldCreator.entities.add(this);
    }

    /**
     *
     */
    public void closeScroll(){
        openScroll = false;
        WorldCreator.entities.remove(this);
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        openScroll = false;
    }

    /**
     *
     * @param mapCoordinates
     */
    public Scroll(Point mapCoordinates) {
        super(mapCoordinates);
    }

    /**
     *
     * @param imageId
     * @param scrollImageId
     */
    public void initializeScroll(int imageId, int scrollImageId){
        setSize(new Size(32, 32));
        if (GamePanel.resources != null) {
            setMainImageId(imageId);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
            scrollImage = (BitmapFactory.decodeResource(GamePanel.resources, scrollImageId));
        }
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
