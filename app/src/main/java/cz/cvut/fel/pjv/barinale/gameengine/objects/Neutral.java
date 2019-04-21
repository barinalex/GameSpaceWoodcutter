package cz.cvut.fel.pjv.barinale.gameengine.objects;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.Activities.MainActivity;

public class Neutral extends GameObject implements View.OnClickListener {
    public Neutral(ArrayList<Bitmap> images, ArrayList<GameObject> inventory, Point mapCoordinates, int[] characteristics, String name, int type) {
        super(images, inventory, mapCoordinates, characteristics, name, type);
    }

    @Override
    public void update(Point point, Point mapPosition) {
    }

    public void speak(){
        Button button = new Button(MainActivity.gameActivity);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
