package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.EntityManager;
import cz.cvut.fel.pjv.barinale.gameengine.objects_2_0.Items.Item;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class MainActivity extends Activity implements View.OnClickListener {
    public static Activity gameActivity;

    private static FrameLayout game;
    private static GamePanel gamePanel;
    private static View inventoryView;
    private static LinearLayout inventoryContainer;

    private static Button menuButton;
    private static Button closeInventory;
    private static Button inventoryButton;
    private static ArrayList<Button> items;

    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameActivity = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gamePanel = new GamePanel(this);
        View gameButtons = getLayoutInflater().inflate(R.layout.game_buttons, null);
        inventoryView = getLayoutInflater().inflate(R.layout.inventory, null);
        inventoryContainer = inventoryView.findViewById(R.id.inventory_linaer_layout);

        game = new FrameLayout(this);
        game.addView(gamePanel);
        game.addView(gameButtons);
        game.addView(inventoryView);
        setContentView(game);

        menuButton = findViewById(R.id.menu);
        closeInventory = findViewById(R.id.closeInventory);
        inventoryButton = findViewById(R.id.inventoryButton);
        menuButton.setOnClickListener(this);
        inventoryButton.setOnClickListener(this);
        closeInventory.setOnClickListener(this);

        game.removeView(inventoryView);

        TextView textView = findViewById(R.id.textView);
        //textView.setText("sadbfjshbcvbdf");
        textView.setVisibility(View.INVISIBLE);

        items = new ArrayList<>();
        music = MediaPlayer.create(getApplicationContext(), R.raw.dark_fallout);
        music.setLooping(true);
        music.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.menu:
                intent = new Intent(this, Menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                music.pause();
                startActivity(intent);
                break;
            case R.id.inventoryButton:
                openInventory();
                game.addView(inventoryView);
                break;
            case R.id.closeInventory:
                closeInventory();
                game.removeView(inventoryView);
                break;
        }
        for (int i = 0; i < items.size(); i++) {
            Button item = items.get(i);
            if (item.getId() == v.getId()) {
                inventoryContainer.removeView(item);
                items.remove(item);
                EntityManager.player.discardItem(i--);
            }
        }
    }

    private void openInventory(){
        gamePanel.setPause(true);
        menuButton.setVisibility(View.INVISIBLE);
        inventoryButton.setVisibility(View.INVISIBLE);
        items = new ArrayList<>();
        ArrayList<Item> inventory = EntityManager.player.getInventory();
        int id = 100;
        for (Item item: inventory) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setBackgroundResource(item.getMainImageId());
            button.getLayoutParams().width = item.getMainImage().getWidth();
            button.getLayoutParams().height = item.getMainImage().getHeight();
            button.setOnClickListener(this);
            button.setId(id++);
            items.add(button);
            inventoryContainer.addView(button);
        }
    }

    private void closeInventory(){
        gamePanel.setPause(false);
        menuButton.setVisibility(View.VISIBLE);
        inventoryButton.setVisibility(View.VISIBLE);
        inventoryContainer.removeAllViews();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Constants.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        Constants.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        Constants.CONFIG_CHANGED = true;
    }
}
