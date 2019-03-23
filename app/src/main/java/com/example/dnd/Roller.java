package com.example.dnd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Roller extends AppCompatActivity implements View.OnClickListener {

    private TextView targetAC;
    private Integer targetAcNum;
    private Button backButton;
    private List<Attack> attacksForRoll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roller);

        // this list is used to store selected attacks for roll
        attacksForRoll = new ArrayList<>();

        // display targetAC
        targetAC = findViewById(R.id.targetAC);
        targetAcNum = MainActivity.sharedPreferences.getInt(MainActivity.targetAC, 0);
        targetAC.setText(targetAcNum.toString());

        // set up back button
        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(Roller.this);
        attacksForRoll = new ArrayList<>();

        // generate a list of attacks for roll
        for (Integer attackId : SelectAttack.getAttackIdsForRoll()){

            Attack newAttack = new Attack(Roller.this);
            newAttack.setAttack(attackId);
            attacksForRoll.add(newAttack);

            // clear attack IDs list
            SelectAttack.getAttackIdsForRoll().clear();
        }

        // generate a list of attacks for roll
        for (Integer attackId : SelectAttack.getAttackIdsForRoll()){

            Attack newAttack = new Attack(Roller.this);
            newAttack.setAttack(attackId);
            attacksForRoll.add(newAttack);

            // clear attack IDs list
            SelectAttack.getAttackIdsForRoll().clear();
        }

        // Roll for each attack

        Integer targetAC = MainActivity.sharedPreferences.getInt(MainActivity.targetAC, 0);

        for (Attack attack : attacksForRoll) {
            System.out.println(attack.rollHit().toString());
            System.out.println(attack.rollDamage());
        }

        // clear attacks
        attacksForRoll.clear();
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
