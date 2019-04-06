package com.example.dnd;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import com.example.dnd.data.CharacterContract;
import com.example.dnd.data.CharacterDatabaseHelper;
import com.example.dnd.data.DiceContract;
import com.example.dnd.data.DiceDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    // created for the listview and showing the characters
    CharacterDatabaseHelper myCharacterDB;
    List<String> characters;

    public static Character character;
    public static Attack attack;
    private Button addNewCharacterButton;
    private Button attackButton;
    private ListView characterListView;


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

        // instantiate the character and attack object
        character = new Character(this);
        attack = new Attack(this);

        // get buttons and set OnClickLister
        addNewCharacterButton = findViewById(R.id.addNewCharacter);
        attackButton = findViewById(R.id.rollAttackbtn);

        addNewCharacterButton.setOnClickListener(this);
        attackButton.setOnClickListener(this);


        //populate an ArrayList<String> from the database and then view it
        characterListView = findViewById(R.id.listView);
        characterListView.setSelector(R.drawable.ic_launcher_background);
        registerForContextMenu(characterListView);

        myCharacterDB = new CharacterDatabaseHelper(this);
        characters = new ArrayList<>();
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characters);

        Cursor data = myCharacterDB.getListContents();

        while(data.moveToNext()){

            // Create a new character name object from the data (Cursor)
            String characterName = data.getString(data.getColumnIndex(CharacterContract.getNameColName()));

            // add a new character name to characters list
            characters.add(characterName);
            characterListView.setAdapter(listAdapter);

        }

        // Set OnItemClickLister to characters' listView
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                character.clearCharacter();
                getOnClickedCharacter(parent, position);
            }

        });

        //characterListView.setLongClickable(true);
        characterListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                getOnClickedCharacter(parent, position);

                return false;
            }
        });

    }

    /*
     * Use this method to grab the on-clicked character and set it to Main.character
     *
     */
    private void getOnClickedCharacter(AdapterView<?> view, Integer position){

        // get a character name from the clicked item
        String selectedCharacter = (String) view.getItemAtPosition(position);

        // get a character's id
        Integer selectedCharacterId = myCharacterDB.getCharacterIdByName(selectedCharacter);

        character.setName(selectedCharacter);
        character.setId(selectedCharacterId);
        character.generateAttacksForCharacter();
    }


    /*
     * ContextMenu is the menu options that are displayed only when the user long-press on the character's name.
     *
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.listView) {

            String[] menuItems = getResources().getStringArray(R.array.options);

            for(int i = 0; i < menuItems.length; i++) {

                menu.add(0, i, i, menuItems[i]);
            }
        }
    }

    /*
     * This method defines the actions that need to be taken by the user's selection of ContextMenu
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Get the name of the selected operation to perform - edit or delete the character?
        String menuItemName = getResources().getStringArray(R.array.options)[item.getItemId()];

        switch (menuItemName){

            case "Edit":
                Intent intent = new Intent(this, CharacterAddEdit.class);
                startActivity(intent);
                break;

            case "Delete":
                character.deleteCharacter();
                Toast.makeText(this, String.format("Deleted %s", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();
                MainActivity.getCharacter().clearCharacter();
                recreate();
                break;

        }

        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addNewCharacter:

                // make sure that the character is not set to any existing character
                character.clearCharacter();

                Log.e("Add Attack Button", "Going to CharacterAddEdit ");
                Intent intent = new Intent(this, CharacterAddEdit.class);
                startActivity(intent);
                break;

            case R.id.rollAttackbtn:

                if(character.getId() == null){
                    Toast.makeText(this, "Pleasee choose a character", Toast.LENGTH_LONG).show();
                    break;
                }
                Log.e("Select Attack Button", "Going to SelectAttack ");

                // create the new intend
                Intent attackButton = new Intent(this, SelectAttack.class);
                startActivity(attackButton);
                break;
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

}
