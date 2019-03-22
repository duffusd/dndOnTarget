package com.example.dnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Roller extends AppCompatActivity implements View.OnClickListener {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roller);

        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(Roller.this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        for (Attack attack : MainActivity.getCharacter().getAttacks()){




        }

        goHome();

    }


    /**
     * Sends user Home after they are finished with attacking
     */
    private void goHome() {
        Button goToRollerButton = findViewById(R.id.BackButton);
        goToRollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Roller.this, MainActivity.class));

            }
        });
    }
}
