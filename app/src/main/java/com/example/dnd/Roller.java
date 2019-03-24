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

public class Roller extends AppCompatActivity implements View.OnClickListener {

    private TextView targetAcText;
    private Integer targetAcNum;
    private Button backButton;
    public static List<RollResult> attackRollResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roller);

        // this list is used to store the roll result from each attack
        attackRollResults = new ArrayList<>();

        // display targetAC
        targetAcNum = MainActivity.sharedPreferences.getInt(MainActivity.targetAC, 0);
        targetAcText = findViewById(R.id.targetAC);
        targetAcText.setText(targetAcNum.toString());

        // set up back button
        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(Roller.this);

        // create attacks for roll
        //generateAttacks();

        // Roll for each attack
        rollAttacks();

        // display result
        ListView resultView = findViewById(R.id.attack_roll_result);
        RollResultListAdapter adapter = new RollResultListAdapter(this, R.layout.roll_attacks_list_view, attackRollResults);

        //LayoutInflater inflater = getLayoutInflater();
        //ViewGroup header = (ViewGroup) inflater.inflate(R.layout.roll_attacks_list_header, resultView, false);
        //resultView.addHeaderView(header, null, false);
        resultView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        goHome();

        // clear the results array
        attackRollResults.clear();
        SelectAttack.getAttackIdsForRoll().clear();
        System.out.println("number of results: " + attackRollResults.size());
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

    private void rollAttacks(){

        Log.i("TargetAC", targetAcNum.toString());

        for (Integer attackId : SelectAttack.getAttackIdsForRoll()){

            Attack attack = new Attack(Roller.this);
            attack.setAttack(attackId);

            // create a place to store the result
            RollResult result = new RollResult();

            // set attack name
            result.setAttackName(attack.getName());

            // get hit
            Integer hit = attack.rollHit();
            Integer hitMod = attack.getModHit();
            result.setHitResult(hit, hitMod);

            // roll damage if hit is greater than target AC
            if(hit > targetAcNum){

                result.setCanRollDamage(true);

                for (int i = 0; i < attack.getNumOfDice(); i++) {

                    Integer damage = attack.getDie().rollDamage();
                    result.getDamages().add(damage);
                }

                result.calculateDamageResult(attack.getModDamage());
            }

            attackRollResults.add(result);

        }

    }

    public static List<RollResult> getAttackRollResults(){
        return attackRollResults;
    }

}
