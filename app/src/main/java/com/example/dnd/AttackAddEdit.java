package com.example.dnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;

public class AttackAddEdit extends AppCompatActivity {

    private AttackDatabaseHelper dbAttacks;
    private CharacterAttacksDatabaseHelper dbCharAttacks;
    private Button btnSave;
    private EditText nameAtk;
    private String selectedAtkName;
    private Integer selectedAtkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_add_edit);
        dbAttacks = new AttackDatabaseHelper(this);
        nameAtk = findViewById(R.id.attackNameEditText);

    }
}
