package com.example.dnd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * <h>Roller</h>
 *
 * This class is used for rolling the attacks for the game and displaying the result
 */
public class Roller extends AppCompatActivity implements View.OnClickListener {

    private TextView targetAcText;
    private Integer targetAcNum;
    private Button backButton;
    private RollResultListAdapter adapter;
    public static List<RollResult> attackRollResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roller);

        // First create the List and the ArrayAdapter
        attackRollResults = new ArrayList<>();
        adapter = new RollResultListAdapter(this, R.layout.roll_attacks_list_view, attackRollResults);

        // Now connect the ArrayAdapter to the ListView
        ListView resultView = findViewById(R.id.attack_roll_result);
        resultView.setAdapter(adapter);

        // display targetAC
        targetAcNum = MainActivity.sharedPreferences.getInt(MainActivity.targetAC, 0);
        targetAcText = findViewById(R.id.targetAC);
        targetAcText.setText(targetAcNum.toString());

        // set up back button
        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(Roller.this);

    }


    @Override
    protected void onStart(){
        super.onStart();

        // Roll attacks
        rollAttacks();

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        goHome();

        // clear the arrays used for rolling attacks
        attackRollResults.clear();
        SelectAttack.getAttackIdsForRoll().clear();
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

    /**
     * This is where Roll Attacks happens
     * */
    private void rollAttacks(){

        Log.i("TargetAC", targetAcNum.toString());
        Attack attack = new Attack(Roller.this);

        // go through attackIds list (which holds attacks selected for rolling)
        for (Integer attackId : SelectAttack.getAttackIdsForRoll()){

            // set up the attack object
            attack.setAttack(attackId);

            // create the new RollResult object for each attack to store the result and display

            RollResult result = new RollResult(attack);

            // roll hit and calculate the result
            result.calculateHitResult();

            // roll damage if the hit was greater than target AC
            if(result.getHitResult() > targetAcNum){

                result.setCanDamage(true);
                result.calculateDamageResult();

            }

            adapter.add(result);
            attack.clear();

        }

    }

}
