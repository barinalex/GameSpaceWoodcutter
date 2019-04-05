package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Button {
    private Rect button;
    private int color;
    private boolean pressed = false;
    private boolean activated = false;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public Button(Rect button, int color){
        this.button = button;
        this.color = color;
    }

    public void update(Point user_point, boolean pressed_button){
        if (button.contains(user_point.x, user_point.y) && pressed_button){
            pressed = true;
        }
        else {
            if (pressed){
                activated = !activated;
            }
            pressed = false;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(button, paint);
    }
}
