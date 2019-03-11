package com.example.dnd;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

public class AttackAddEdit extends AppCompatActivity {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private Button btnSave;
    private EditText nameAtk;
    private EditText hitModifier;
    private EditText damageModifier;
    private String selectedAtkName;
    private Integer selectedAtkId;

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


        // set onclick lister to save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer newAttackId = addAttack(); // add an attack and get a new attackID
                Integer characterId = MainActivity.sharedPreferences.getInt(MainActivity.SharedPrefCharacterId, -1);
                dbCharAttacks.addCharacterAttack(characterId == -1 ? null : characterId, newAttackId);

            }
        });

    }

    private Integer addAttack(){

        String newAttackName = nameAtk.getText().toString().trim();
        String newHitModifier = hitModifier.getText().toString().trim();
        String newDamageModifier = damageModifier.getText().toString().trim();

        Integer newRowId = dbAttacks.addAttack(newAttackName, (newHitModifier.isEmpty() ? 0 : Integer.parseInt(newHitModifier)), (newDamageModifier.isEmpty() ? 0 : Integer.parseInt(newDamageModifier)), 2);
        return newRowId;
    }



}
