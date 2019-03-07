package com.example.dnd;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.AttackDiceDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

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

    public int roll(int AC) {
        // get the text view from the View to get the user input
        EditText textAC = findViewById(R.id.targetACEditText);

        // instantiate the objects to open and manipulate the share preferences file
        SharedPreferences sPref = getSharedPreferences("com.example.dnd_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editPref = sPref.edit();

        // add the string with AC to the shared preferences file
        editPref.putString("targetAC", textAC.toString());

        // apply the changes to the sharedPreferences file
        editPref.apply();

        Log.d(TAG, "Target AC saved to sharedpref file \"com.example.dnd_prefs\"");

        //TODO: run the roll methods from the attack set object

        return -1;

    }
}
