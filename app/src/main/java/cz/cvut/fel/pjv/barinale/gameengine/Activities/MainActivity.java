package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class MainActivity extends Activity implements View.OnClickListener {
    private static FrameLayout game;
    private static GamePanel gamePanel;
    private static View inventoryLayout;

    private static Button menuButton;
    private static Button closeInventory;
    private static Button inventoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gamePanel = new GamePanel(this);
        View gameButtons = getLayoutInflater().inflate(R.layout.game_buttons, null);
        inventoryLayout = getLayoutInflater().inflate(R.layout.inventory, null);
        LinearLayout linearLayout = inventoryLayout.findViewById(R.id.inventory_linaer_layout);

        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setBackgroundResource(R.drawable.axe);
            linearLayout.addView(button);
        }
        /*
        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(params);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setBackgroundResource(R.drawable.axe);
            linearLayout.addView(button);
        }
        scrollView.addView(linearLayout);
        */
        game = new FrameLayout(this);
        game.addView(inventoryLayout);
        game.addView(gamePanel);
        game.addView(gameButtons);
        //game.addView(scrollView);
        setContentView(game);

        menuButton = findViewById(R.id.menu);
        closeInventory = findViewById(R.id.closeInventory);
        inventoryButton = findViewById(R.id.inventoryButton);
        menuButton.setOnClickListener(this);
        inventoryButton.setOnClickListener(this);
        closeInventory.setOnClickListener(this);
        game.removeView(inventoryLayout);

        TextView textView = findViewById(R.id.textView);
        //textView.setText("sadbfjshbcvbdf");
        textView.setVisibility(View.INVISIBLE);

        /*LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.BOTTOM);
        Button button = new Button(this);
        button.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        button.setBackgroundResource(R.drawable.axe);
        linearLayout.addView(button);
        game.addView(linearLayout);
        */
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
                /*intent = new Intent(this, Inventory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);*/
                gamePanel.setPause(true);
                menuButton.setVisibility(View.INVISIBLE);
                inventoryButton.setVisibility(View.INVISIBLE);
                game.addView(inventoryLayout);
                break;
            case R.id.closeInventory:
                gamePanel.setPause(false);
                menuButton.setVisibility(View.VISIBLE);
                inventoryButton.setVisibility(View.VISIBLE);
                game.removeView(inventoryLayout);
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
