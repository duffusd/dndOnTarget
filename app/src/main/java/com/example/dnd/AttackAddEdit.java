package com.example.dnd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;
import com.example.dnd.data.DiceContract;
import com.example.dnd.data.DiceDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttackAddEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private DiceDatabaseHelper dbDice;
    private Button btnSave;
    private Button btnDelete;
    private EditText nameAtk;
    private EditText hitModifier;
    private EditText damageModifier;
    private EditText numDice;
    private String attackNameStr;
    private String hitModifierStr;
    private String damageModifierStr;
    private String numDiceStr;
    private String tag = "AttackAddEdit";
    private String dieType;
    private Long dieId = null;
    private RecyclerView list;
    private Spinner spinner;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attack_add_edit);

        dbAttacks = new AttackDatabaseHelper(this);
        dbCharAttacks = new CharacterAttacksDatabaseHelper(this);
        dbDice = new DiceDatabaseHelper(this);

        btnSave = findViewById(R.id.attackSaveButton);
        btnDelete = findViewById(R.id.deleteAttackButton);
        nameAtk = findViewById(R.id.attackNameEditText);
        hitModifier = findViewById(R.id.hitModEditText);
        damageModifier = findViewById(R.id.damageModifierText);
        numDice = findViewById(R.id.numberOfDice);
        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this
                , R.array.diceType_array
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);

        attackNameStr = null;
        hitModifierStr = null;
        damageModifierStr = null;
        numDiceStr = null;

        // Set the field values if the attack was selected to edit in the previous activity
        if (MainActivity.getAttack().getId() != null) {

            nameAtk.setText(MainActivity.getAttack().getName());
            hitModifier.setText(MainActivity.getAttack().getModHit().toString());
            damageModifier.setText(MainActivity.getAttack().getModDamage().toString());
            numDice.setText(MainActivity.getAttack().getNumOfDice().toString());
            spinner.setSelection(MainActivity.getAttack().getDiceId());


        } else {

            btnDelete.setEnabled(false);
        }
    }

    @Override
    protected void onStart(){

        super.onStart();

        // set onclick lister to save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attackNameStr = nameAtk.getText().toString().trim();
                hitModifierStr = hitModifier.getText().toString().trim();
                damageModifierStr = damageModifier.getText().toString().trim();
                numDiceStr = numDice.getText().toString().trim();

                // use these values for adding/updating
                String attackName = null;
                Integer hitModifier = null;
                Integer damageModifier = null;
                Integer diceId = dieId.intValue();
                Integer numOfDice = null;

                // validate the entries by the user
                try{

                    attackName = MainActivity.getAttack().validateAttackName(attackNameStr);
                    hitModifier = MainActivity.getAttack().validateHitModifier(hitModifierStr);
                    damageModifier = MainActivity.getAttack().validateDamageModifier(damageModifierStr);
                    diceId = MainActivity.getAttack().validateDiceId(diceId.intValue());
                    numOfDice = MainActivity.getAttack().validateNumOfDie(numDiceStr);

                } catch(Exception e){

                    Toast.makeText(AttackAddEdit.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(tag, e.getMessage());
                    return;
                }


                // add a new attack
                if(MainActivity.getAttack().getId() == null){

                    Integer newId = MainActivity.getAttack().addAttack(attackName,
                            hitModifier,
                            damageModifier,
                            diceId,
                            numOfDice);

                    // create a new attack object and add it to attacks array list of the selected character object
                    Attack newAttack = new Attack(AttackAddEdit.this);
                    newAttack.setAttack(newId);
                    MainActivity.getCharacter().addAttack(newAttack);

                    // Also, add a new attack to CharacterAttack table
                    dbCharAttacks.addCharacterAttack(MainActivity.getCharacter().getId(), newId);

                    Toast.makeText(AttackAddEdit.this, String.format("Added a new attack for %s",
                            MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

                }
                // update the selected attack
                else {

                    /** update name **/

                    if(attackName != MainActivity.getAttack().getName()){

                        MainActivity.getAttack().updateName(attackName);
                    }


                    /** update hit modifier **/

                    if(hitModifier != MainActivity.getAttack().getModHit()){

                        MainActivity.getAttack().updateHitModifier(hitModifier);
                    }


                    /** update damage modifier **/

                    if(damageModifier != MainActivity.getAttack().getModDamage()){

                        MainActivity.getAttack().updateDamageModifier(damageModifier);
                    }


                    /** update dice ID and die **/

                    if(diceId != MainActivity.getAttack().getDiceId()) {

                        MainActivity.getAttack().updateDiceID(diceId);
                        MainActivity.getAttack().getDie().setDieId(diceId);
                        MainActivity.getAttack().getDie().updateSides();

                    }

                    // update the number of die
                    if(numOfDice != MainActivity.getAttack().getNumOfDice()) {

                        MainActivity.getAttack().updateNumOfDie(numOfDice);
                    }



                    Toast.makeText(AttackAddEdit.this, String.format("Updated %s",
                            MainActivity.getAttack().getName()), Toast.LENGTH_LONG).show();

                }

                // go back to character addd/edit activity
                Intent intent = new Intent(AttackAddEdit.this, CharacterAddEdit.class);
                startActivity(intent);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.getAttack().deleteAttack(MainActivity.getAttack().getId());
                Toast.makeText(AttackAddEdit.this, String.format("Deleted %s", MainActivity.getAttack().getName()), Toast.LENGTH_LONG).show();
                MainActivity.getCharacter().getAttacks().remove(MainActivity.getAttack());
                MainActivity.getAttack().clear();

            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(tag,"Item was selected in Spinner");
        if (i <= 6) {
            dieType = adapterView.getSelectedItem().toString();
            dieId = adapterView.getSelectedItemId();
        }
        Log.d(tag,"Item at position is: " + dieId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        /*

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.getAttack().deleteAttack(MainActivity.getAttack().getId());
                MainActivity.getCharacter().removeAttack(MainActivity.getAttack());
                Toast.makeText(AttackAddEdit.this, String.format("Deleted %s", MainActivity.getAttack().getName()), Toast.LENGTH_LONG).show();

                // clear the text and attack object
                nameAtk.setText(null);
                hitModifier.setText(null);
                damageModifier.setText(null);
                MainActivity.getAttack().clear();
            }
        });

        */
    }
}
