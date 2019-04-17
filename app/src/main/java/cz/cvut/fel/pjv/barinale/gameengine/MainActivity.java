package cz.cvut.fel.pjv.barinale.gameengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class MainActivity extends Activity implements View.OnClickListener {
    FrameLayout game;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GamePanel gamePanel = new GamePanel(this);
        View gameButtons = getLayoutInflater().inflate(R.layout.game_buttons, null, false);
        game = new FrameLayout(this);
        game.addView(gamePanel);
        game.addView(gameButtons);
        setContentView(game);
        Button menu = findViewById(R.id.menu);
        Button inventory = findViewById(R.id.inventory);
        TextView textView = findViewById(R.id.textView);
        //textView.setText("sadbfjshbcvbdf");
        textView.setVisibility(View.INVISIBLE);
        menu.setOnClickListener(this);
        inventory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                Intent intent = new Intent(this, Menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.inventory:
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
