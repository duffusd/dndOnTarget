package com.example.dnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.AttackDiceDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    CharacterDatabaseHelper characterDbHelper;
    AttackDiceDatabaseHelper attackDiceDbHelper;
    AttackDatabaseHelper attackDbHelper;
    CharacterAttacksDatabaseHelper characterAttacksDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //characterDbHelper = new CharacterDatabaseHelper(this);
        //attackDiceDbHelper = new AttackDiceDatabaseHelper(this);
        //attackDbHelper = new AttackDatabaseHelper(this);
        characterAttacksDbHelper = new CharacterAttacksDatabaseHelper(this);


        //attackDbHelper.addHitModifier(100);
        //attackDbHelper.updateHitModifier(2, 0);
        //attackDbHelper.deleteAttack(2);

        //characterDbHelper.addName("test character");
        //characterDbHelper.updateName("Eric", 1);
        //characterDbHelper.deleteCharacter(2);

        //attackDiceDbHelper.addAttackDice("1d24");
        //attackDiceDbHelper.updateAttackDice(1, "1d1");
        //attackDiceDbHelper.deleteAttackDice(1);
        //attackDbHelper.addAttack(1500, 150, 1);

        characterAttacksDbHelper.addCharacterAttack(1, 2);

        setContentView(R.layout.activity_main);
    }
}
