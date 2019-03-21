package com.example.dnd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttackAddEdit extends AppCompatActivity {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private Button btnSave;
    private Button btnDelete;
    private EditText nameAtk;
    private EditText hitModifier;
    private EditText damageModifier;
    private String tag = "AttackAddEdit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_add_edit);
        dbAttacks = new AttackDatabaseHelper(this);
        dbCharAttacks = new CharacterAttacksDatabaseHelper(this);

        btnSave = findViewById(R.id.attackSaveButton);
        btnDelete = findViewById(R.id.deleteAttackButton);
        nameAtk = findViewById(R.id.attackNameEditText);
        hitModifier = findViewById(R.id.hitModEditText);
        damageModifier = findViewById(R.id.damageModifierText);


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

                // delete this when dice is implemented
                Integer diceId = new Random().nextInt(5);


                // add a new attack
                if(MainActivity.getAttack().getId() == null){

                    Integer newId = MainActivity.getAttack().addAttack(newAttackName,
                            newHitModifier.isEmpty() ? 0 : Integer.parseInt(newHitModifier),
                            newDamageModifier.isEmpty() ? 0 : Integer.parseInt(newDamageModifier), diceId);

                    // create a new attack object and add it to attacks array list of the selected character object
                    Attack newAttack = new Attack(AttackAddEdit.this);
                    newAttack.setAttack(newId);
                    MainActivity.getCharacter().addAttack(newAttack);

                    // Also, add a new attack to CharacterAttack table
                    dbCharAttacks.addCharacterAttack(MainActivity.getCharacter().getId(), newId);

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

                // go back to character addd/edit activity
                MainActivity.getAttack().clear();
                Intent intent = new Intent(AttackAddEdit.this, CharacterAddEdit.class);
                startActivity(intent);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.getAttack().deleteAttack(MainActivity.getAttack().getId());

            }
        });


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
    }
}
