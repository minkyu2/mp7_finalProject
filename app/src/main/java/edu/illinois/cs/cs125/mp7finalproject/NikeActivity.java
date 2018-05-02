package edu.illinois.cs.cs125.mp7finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.illinois.cs.cs125.mp7finalproject.R;

public class NikeActivity extends AppCompatActivity {

    Button indoorButton, outdoorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nike);

        indoorButton = (Button) findViewById(R.id.indoorBtn);
        outdoorButton = (Button) findViewById(R.id.outdoorBtn);

        indoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indoorIntent = new Intent(NikeActivity.this, IndoorActivity.class);
                startActivity(indoorIntent);
            }
        });

        outdoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outdoorIntent = new Intent(NikeActivity.this, OutdoorActivity.class);
                startActivity(outdoorIntent);
            }
        });

    }
}
