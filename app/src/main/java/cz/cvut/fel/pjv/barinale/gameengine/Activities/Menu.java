package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public class Menu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(ImageArchive.images == null) ImageArchive.read_images();
        setContentView(R.layout.menu);
        Button button_resume = findViewById(R.id.button_resume);
        Button button_start = findViewById(R.id.button_start);
        Button buttonSave = findViewById(R.id.save);
        Button buttonLoad = findViewById(R.id.load);
        Button buttonQuit = findViewById(R.id.buttonQuit);
        button_resume.setOnClickListener(this);
        button_start.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.button_resume:
                if (MainActivity.gameActivity != null) {
                    intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                break;
            case R.id.button_start:
                if (MainActivity.gameActivity != null) {
                    MainActivity.gameActivity.finish();
                }
                GameObjectManager.gameObjects = null;
                GameObjectManager.background = null;
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.save:
                Utils.saveGameState(this);
                break;
            case R.id.load:
                //Utils.loadGameState(this);
                if (MainActivity.gameActivity != null) {
                    MainActivity.gameActivity.finish();
                }
                GameObjectManager.gameObjects = null;
                GameObjectManager.background = null;
                GameObjectManager.loadFromFile = true;
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonQuit:
                MainActivity.gameActivity.finish();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
        }
    }
}
