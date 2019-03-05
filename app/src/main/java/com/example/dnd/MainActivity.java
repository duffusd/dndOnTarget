package com.example.dnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    CharacterDatabaseHelper characterDbHelper;
    AttackDatabaseHelper attackDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        characterDbHelper = new CharacterDatabaseHelper(this);
        attackDbHelper = new AttackDatabaseHelper(this);

        //attackDbHelper.addHitModifier(100);
        //attackDbHelper.updateHitModifier(2, 0);
        //attackDbHelper.deleteAttack(2);

        //characterDbHelper.addName("test character");
        //characterDbHelper.updateName("Eric", 1);
        //characterDbHelper.deleteCharacter(2);

        setContentView(R.layout.activity_main);
    }
}
