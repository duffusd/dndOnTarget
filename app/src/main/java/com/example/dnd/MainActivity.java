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
import android.util.Log;
import android.widget.EditText;

import com.example.dnd.data.CharacterContract;
import com.example.dnd.data.CharacterDatabaseHelper;
import com.example.dnd.data.DiceContract;
import com.example.dnd.data.DiceDatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    // created for the listview and showing the characters
    CharacterDatabaseHelper myCharacterDB;
    List<String> characters;

    public static Character character;
    public static Attack attack;
    private Button addEditCharacterButton;
    private Button attackButton;
    private EditText targetACText;
    public static final String targetAcSharedPreference = "TargetAcSharedPref";
    public static final String targetAC = "TargetAC";
    public static SharedPreferences sharedPreferences;

    @Override
    protected  void onStart(){
        super.onStart();

      // insert rows to Dice table if it hasn't been populated yet
        DiceDatabaseHelper dbHelper = new DiceDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT Count(*) FROM " + DiceContract.getTableName(), null);

        while(data.moveToNext()){

            Integer numOfRows = data.getInt(0);

            if(!numOfRows.equals(6))
                dbHelper.insertNumbers();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up shared preferences for this app
        sharedPreferences = getSharedPreferences(targetAcSharedPreference, Context.MODE_PRIVATE);
        //clearSharedPreferences(); // clear existing shared preferences

        // instantiate the character and attack object
        character = new Character(this);
        attack = new Attack(this);

        // get buttons and set OnClickLister
        addEditCharacterButton = findViewById(R.id.addEditAttackButton);
        attackButton = findViewById(R.id.rollAttackbtn);

        addEditCharacterButton.setOnClickListener(this);
        attackButton.setOnClickListener(this);

        // get targetAC textfield
        targetACText = findViewById(R.id.targetACEditText);

        //populate an ArrayList<String> from the database and then view it
        ListView listView = findViewById(R.id.listView);
        listView.setSelector(R.drawable.ic_launcher_background);

        myCharacterDB = new CharacterDatabaseHelper(this);
        characters = new ArrayList<>();
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characters);

        Cursor data = myCharacterDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){

                // Create a new character name object from the data (Cursor)
                String characterName = data.getString(data.getColumnIndex(CharacterContract.getNameColName()));

                // add a new character name to characters list
                characters.add(characterName);
                listView.setAdapter(listAdapter);
            }
        }

        // Set OnItemClickLister to characters' listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get a character name from the clicked item
                String selectedCharacter = (String) parent.getItemAtPosition(position);

                // get a character's id
                Integer selectedCharacterId = myCharacterDB.getCharacterIdByName(selectedCharacter);

                character.setName(selectedCharacter);
                character.setId(selectedCharacterId);
                character.generateAttacksForCharacter();

            }

        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addEditAttackButton:
                Log.e("Add Attack Button", "Going to CharacterAddEdit ");
                Intent intent = new Intent(this, CharacterAddEdit.class);
                startActivity(intent);
                break;
            case R.id.rollAttackbtn:
                Log.e("Select Attack Button", "Going to SelectAttack ");

                // set up targetAC shared preference and get the value

                String targetACString = targetACText.getText().toString().trim();

                // first, make sure targetAC is not empty

                if(targetACString.isEmpty()){

                    Toast.makeText(this, "What is the target AC?", Toast.LENGTH_LONG).show();
                    break;

                } else {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Integer targetAcNum = null;

                    try {

                        // if this fails, the value for targetAC is wrong
                        targetAcNum = Integer.parseInt(targetACString);

                    } catch (Exception e){
                        Toast.makeText(this, "Invalid targetAC", Toast.LENGTH_LONG).show();
                        break;
                    }

                    editor.putInt(targetAC, targetAcNum);
                    editor.commit();

                    // create the new intend
                    Intent attackButton = new Intent(this, SelectAttack.class);
                    startActivity(attackButton);

                    break;
                }

        }

    }

    /**
     *  Returns the Character object
     *  @author Atsuko Critchfield (Takanabe)
     *  @return character
     */
    public static Character getCharacter(){
        return character;
    }

    /**
     *  Returns the Attack object
     *  @author Atsuko Critchfield (Takanabe)
     *  @return attack
     */
    public static Attack getAttack(){
        return attack;
    }

    /**
     *  Set the Attack object
     *  @author Atsuko Critchfield (Takanabe)
     *  @return void
     */
    public static void setAttack(Attack newAttack){
        attack = newAttack;
    }

    /*
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

    */
}
