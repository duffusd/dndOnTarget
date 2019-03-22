package com.example.dnd;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class AttackAddEdit extends AppCompatActivity {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private DiceDatabaseHelper dbDice;
    private Button btnSave;
    private Button btnDelete;
    private EditText nameAtk;
    private EditText hitModifier;
    private EditText damageModifier;
    private String tag = "AttackAddEdit";
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
        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this
                , R.array.diceType_array
                , android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        // Set the field values if the attack was selected to edit in the previous activity
        if(MainActivity.getAttack().getId() != null){

            nameAtk.setText(MainActivity.getAttack().getName());
            hitModifier.setText(MainActivity.getAttack().getModHit().toString());
            damageModifier.setText(MainActivity.getAttack().getModDamage().toString());

        } else {

            btnDelete.setEnabled(false);
        }

        /**** set onclick lister to save button *****/

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                String newAttackName = nameAtk.getText().toString().trim();
                String newHitModifier = hitModifier.getText().toString().trim();
                String newDamageModifier = damageModifier.getText().toString().trim();


                // add a new attack
                if(MainActivity.getAttack().getId() == null){

                    MainActivity.getAttack().addAttack(newAttackName,
                            newHitModifier.isEmpty() ? 0 : Integer.parseInt(newHitModifier),
                            newDamageModifier.isEmpty() ? 0 : Integer.parseInt(newDamageModifier),
                            null);

                    // add a new attack to CharacterAttack table
                    dbCharAttacks.addCharacterAttack(MainActivity.getCharacter().getId(), MainActivity.attack.getId());

                    Toast.makeText(AttackAddEdit.this, String.format("Added a new attack for %s",
                            MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

                }
                // update a selected attack
                else {

                    // update name
                    if(newAttackName != MainActivity.getAttack().getName()){
                        MainActivity.getAttack().updateName(newAttackName);
                    }

                    // update hit modifier
                    if(Integer.parseInt(newHitModifier) != MainActivity.getAttack().getModHit()){
                        MainActivity.getAttack().updateHitModifier(Integer.parseInt(newHitModifier));
                    }

                    // update damage modifier
                    if(Integer.parseInt(newDamageModifier) != MainActivity.getAttack().getModDamage()){
                        MainActivity.getAttack().updateDamageModifier(Integer.parseInt(newDamageModifier));
                    }

                    Toast.makeText(AttackAddEdit.this, String.format("Updated %s",
                            MainActivity.getAttack().getName()), Toast.LENGTH_LONG).show();

                }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.getAttack().deleteAttack(MainActivity.getAttack().getId());

            }
        });


    }
}
