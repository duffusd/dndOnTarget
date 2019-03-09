package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    //CharacterDatabaseHelper characterDbHelper;
    //AttackDiceDatabaseHelper attackDiceDbHelper;
    //AttackDatabaseHelper attackDbHelper;


    // created for the listview and showing the characters
    CharacterDatabaseHelper myCharacterDB;


    private Button addEditCharacterButton;

    public static final String SharedPrefs = "CharacterPref";
    public static final String SharedPrefCharacterName = "characterNameKey";
    public static final String SharedPrefCharacterId = "characterId";

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set onClickLister to AddEdit Character button
        addEditCharacterButton = findViewById(R.id.addEditCharacterButton);
        addEditCharacterButton.setOnClickListener(this);

        // set up shared preferences for this app
        sharedPreferences = getSharedPreferences(SharedPrefs, Context.MODE_PRIVATE);

        // Insert dice numbers to diceTable if the table is empty
        DiceDatabaseHelper diceDbHelper = new DiceDatabaseHelper(this);
        SQLiteDatabase diceDb = diceDbHelper.getReadableDatabase();

        Cursor cursor = diceDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = \"" +
                DiceContract.getTableName() + "\"", null);
        int result = cursor.getCount();
        if (result == 0){
            diceDbHelper.insertNumbers();
        }

        //populate an ArrayList<String> from the database and then view it
        ListView listView = findViewById(R.id.listView);
        myCharacterDB = new CharacterDatabaseHelper(this);
        ArrayList<String> theList = new ArrayList<>();
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);

        Cursor data = myCharacterDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                listView.setAdapter(listAdapter);
            }
        }

        // Set OnItemClickLister to characters' listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCharacter = (String)parent.getItemAtPosition(position);
                String selectedCharacterId = myCharacterDB.getCharacterIdByName(selectedCharacter);
                myCharacterDB.getCharacterIdByName(selectedCharacter);

                // save the name of the selected character in shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SharedPrefCharacterName, selectedCharacter);
                editor.putString(SharedPrefCharacterId, selectedCharacterId);
                editor.commit();
            }
        });
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

    public static void clearSharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
