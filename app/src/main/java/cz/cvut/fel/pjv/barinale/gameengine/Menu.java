package cz.cvut.fel.pjv.barinale.gameengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button button_resume = (Button) findViewById(R.id.button_resume);
        Button button_start = (Button) findViewById(R.id.button_start);
        button_resume.setOnClickListener(this);
        button_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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
        }
    }
}
