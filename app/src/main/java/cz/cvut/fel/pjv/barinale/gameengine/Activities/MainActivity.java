package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class MainActivity extends Activity implements View.OnClickListener {
    FrameLayout game;
    View inventoryLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GamePanel gamePanel = new GamePanel(this);
        View gameButtons = getLayoutInflater().inflate(R.layout.game_buttons, null, false);
        //inventoryLayout = getLayoutInflater().inflate(R.layout.inventory, null, false);
        //inventoryLayout.setVisibility(View.INVISIBLE);
        game = new FrameLayout(this);
        game.addView(gamePanel);
        game.addView(gameButtons);
        //game.addView(inventoryLayout);
        setContentView(game);
        Button menu = findViewById(R.id.menu);
        Button inventory = findViewById(R.id.inventoryButton);
        TextView textView = findViewById(R.id.textView);
        //textView.setText("sadbfjshbcvbdf");
        textView.setVisibility(View.INVISIBLE);
        menu.setOnClickListener(this);
        inventory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.menu:
                intent = new Intent(this, Menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.inventoryButton:
                intent = new Intent(this, Inventory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //setContentView(game);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Constants.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Constants.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        Constants.CONFIG_CHANGED = true;
    }
}
