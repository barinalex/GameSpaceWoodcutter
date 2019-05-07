package cz.cvut.fel.pjv.barinale.gameengine.objects_2_0;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BlueWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.BrownWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.CherryWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.GreenWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.OrangeWood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.Wood;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Wood.YellowWood;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Size;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class Teleport extends Entity{
    private boolean activated = false;
    private String location;
    public Teleport(Point mapCoordinates) {
        super(mapCoordinates);
        setSize(new Size(90, 49));
        if (GamePanel.resources != null) {
            setMainImageId(R.drawable.teleport_inactive);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
        }
        setBody();
        setActiveZone();
    }

    @Override
    public void update(Point userPoint, Point mapPosition) {
        getScreenCoordinates().set(getMapCoordinates().x + mapPosition.x,
                getMapCoordinates().y + mapPosition.y);
        if (!activated && (location = checkTeleportationConditions()) != null){
            setMainImageId(R.drawable.teleport);
            setMainImage(BitmapFactory.decodeResource(GamePanel.resources, getMainImageId()));
            activated = true;
        }
    }

    @Override
    public void setActiveZone() {
        getActiveZone().set(getMapCoordinates().x - getMainImage().getWidth() / 4,
                getMapCoordinates().y - getMainImage().getHeight() / 4,
                getMapCoordinates().x + getMainImage().getWidth() / 4,
                getMapCoordinates().y + getMainImage().getHeight() / 4);
    }

    public void pushWood(ArrayList<Wood> woods){
        for (Wood wood: woods){
            getInventory().add(wood);
        }
    }

    public void teleportate(){
        if (activated) {
            EntityManager.createRandomMap(location, true);
        }
    }

    private String checkTeleportationConditions(){
        int green = 0, brown = 0, yellow = 0, orange = 0, blue = 0, cherry = 0;
        for (Item item: getInventory()){
            if (item instanceof GreenWood) green++;
            if (item instanceof BrownWood) brown++;
            if (item instanceof YellowWood) yellow++;
            if (item instanceof OrangeWood) orange++;
            if (item instanceof BlueWood) blue++;
            if (item instanceof CherryWood) cherry++;
        }
        if (cherry > 2){
            return "earth_land";
        }
        if (green > 0 && brown > 0 && yellow > 0 && orange > 0 && blue > 0){
            return "cherry_land";
        }
        if (green > 1 && blue > 1){
            return "yellow_land";
        }
        return null;
    }
}
