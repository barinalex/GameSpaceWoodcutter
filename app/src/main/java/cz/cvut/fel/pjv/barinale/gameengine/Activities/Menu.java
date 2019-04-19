package cz.cvut.fel.pjv.barinale.gameengine.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cz.cvut.fel.pjv.barinale.gameengine.R;
import cz.cvut.fel.pjv.barinale.gameengine.functionality.GameObjectManager;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Constants;
import cz.cvut.fel.pjv.barinale.gameengine.utils.Utils;

public class Menu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button button_resume = findViewById(R.id.button_resume);
        Button button_start = findViewById(R.id.button_start);
        Button buttonSave = findViewById(R.id.save);
        Button buttonLoad = findViewById(R.id.load);
        button_resume.setOnClickListener(this);
        button_start.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.button_resume:
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.button_start:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.save:
                Utils.saveGameState(this);
                break;
            case R.id.load:
                Utils.loadGameState(this);
                //if (GameObjectManager.player != null)
                    //GameObjectManager.player.getCharacteristics()[Constants.SPEED] = Utils.loadGameState(this);
                break;
        }
    }
}
