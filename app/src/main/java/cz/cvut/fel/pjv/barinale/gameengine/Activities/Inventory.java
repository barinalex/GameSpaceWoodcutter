package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import cz.cvut.fel.pjv.barinale.gameengine.R;

public class Inventory extends Activity implements View.OnClickListener {
    FrameLayout inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventory = new FrameLayout(this);
        View inventoryLayout = getLayoutInflater().inflate(R.layout.inventory, null);
        inventory.addView(inventoryLayout);
        setContentView(inventory);
        Button close = findViewById(R.id.closeInventory);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.closeInventory:
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }
}
