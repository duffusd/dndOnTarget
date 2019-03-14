package com.example.dnd;

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

public class AttackAddEdit extends AppCompatActivity {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private Button btnSave;
    private EditText nameAtk;
    private EditText hitModifier;
    private EditText damageModifier;
    private String selectedAtkName;
    private Integer selectedAtkId;
    private String tag = "AttackAddEdit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_add_edit);
        dbAttacks = new AttackDatabaseHelper(this);
        btnSave = findViewById(R.id.attackSaveButton);
        nameAtk = findViewById(R.id.attackNameEditText);
        hitModifier = findViewById(R.id.hitModEditText);
        damageModifier = findViewById(R.id.damageModifierText);
        dbCharAttacks = new CharacterAttacksDatabaseHelper(this);


        /**** set onclick lister to save button *****/

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                String newAttackName = nameAtk.getText().toString().trim();
                String newHitModifier = hitModifier.getText().toString().trim();
                String newDamageModifier = damageModifier.getText().toString().trim();

                MainActivity.getAttack().addAttack(newAttackName,
                        newHitModifier.isEmpty() ? 0 : Integer.parseInt(newHitModifier),
                        newDamageModifier.isEmpty() ? 0 : Integer.parseInt(newDamageModifier),
                        null);

                // Logging for debugging
                Log.d(tag, String.format("AttackId: %s", MainActivity.getAttack().getId().toString()));

                if(MainActivity.getCharacter().getId() != null){

                    // Logging for debugging
                    Log.d(tag, String.format("CharacterID: %s", MainActivity.getCharacter().getId()));

                    dbCharAttacks.addCharacterAttack(MainActivity.getCharacter().getId(), MainActivity.attack.getId());

                    Toast.makeText(AttackAddEdit.this, String.format("Added a new attack for %s", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(AttackAddEdit.this, "Added a new attack!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
