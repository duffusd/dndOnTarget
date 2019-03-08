package com.example.dnd;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.AttackDiceDatabaseHelper;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;
import com.example.dnd.data.DiceContract;
import com.example.dnd.data.DiceDatabaseHelper;

import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CharacterDatabaseHelper characterDbHelper;
    AttackDiceDatabaseHelper attackDiceDbHelper;
    AttackDatabaseHelper attackDbHelper;

    //CharacterAttacksDatabaseHelper characterAttacksDbHelper;

    // created for the listview and showing the characters
    CharacterDatabaseHelper myDB;

    private Button addEditCharacterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set onClickLister to AddEdit Character button
        addEditCharacterButton = findViewById(R.id.addEditCharacterButton);
        addEditCharacterButton.setOnClickListener(this);

        /* **********************************************
         * Check if diceTable is already in Dice.db.
         * SQLiteOpenHelper onCreate() and onUpgrade() callbacks are invoked
         * when the database is actually opened, for example by a call to getWritableDatabase().
         * By calling insertNumbers(), which calls getWritableDatabase(), the table gets created
         * and values be inserted
         * This is to avoid unique constraint error on number column
         * **************/

        DiceDatabaseHelper diceDbHelper = new DiceDatabaseHelper(this);
        SQLiteDatabase diceDb = diceDbHelper.getReadableDatabase();

        Cursor cursor = diceDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = \"" +
                DiceContract.getTableName() + "\"", null);
        int result = cursor.getCount();
        if (result == 0){
            diceDbHelper.insertNumbers();
        }

        //populate an ArrayList<String> from the database and then view it

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new CharacterDatabaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

       // attackDbHelper.addHitModifier(100);
       // attackDbHelper.addName("Banshing Blow")
        //attackDbHelper.updateHitModifier(2, 0);
        //attackDbHelper.deleteAttack(2);

        //  Test Characters to add to the DB
//        characterDbHelper.addName("Oazon");
//        characterDbHelper.addName("Balrog");
//        characterDbHelper.addName("Vinn");
//        characterDbHelper.addName("Lini");
//        characterDbHelper.addName("Kaliden");
//        characterDbHelper.addName("Yasha");
//        characterDbHelper.addName("Tyrien");


        //characterDbHelper.updateName("Eric", 1);
        //characterDbHelper.deleteCharacter(2);

        //attackDiceDbHelper.addAttackDice("1d24");
        //attackDiceDbHelper.updateAttackDice(1, "1d1");
        //attackDiceDbHelper.deleteAttackDice(1);
//        attackDbHelper.addAttack(5, 1, 1);
//        attackDbHelper.addAttack(6, 2, 1);
//        attackDbHelper.addAttack(7, 3, 1);
//        attackDbHelper.addAttack(8, 4, 1);


        //characterAttacksDbHelper.addCharacterAttack(1, 2);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addEditCharacterButton:
                setContentView(R.layout.activity_character_add_edit);
                Intent intent = new Intent(this, CharacterAddEdit.class);
                startActivity(intent);
        }

    }

}
