package com.example.dnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import com.example.dnd.data.CharacterDatabaseHelper;

public class CharacterAddEdit extends AppCompatActivity {
    public static final String ATTACKS_ID = "com.example.dnd.ATTACKS_ID";

    private CharacterDatabaseHelper myDB;
    private Button btnAdd;
    private Button deleteCharacterButton;
    private Button btnAddEditAttack;
    private EditText editText;
    private String selectedCharacterName;
    private Integer selectedCharacterId;
    private ListView listAttack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);
        myDB = new CharacterDatabaseHelper(this);
        selectedCharacterName = MainActivity.sharedPreferences.getString(MainActivity.SharedPrefCharacterName, null);

        // get the buttons
        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        listAttack = findViewById(R.id.attackListView);
        btnAdd = findViewById(R.id.saveCharacterButton);
        editText =  findViewById(R.id.characterNameEditText);


        /* If the user chose to edit the existing character, populate this field with that character's name
          * and get its id * */

        if(selectedCharacterName != null){

            editText.setText(selectedCharacterName);
            String id_str = myDB.getCharacterIdByName(selectedCharacterName);
            selectedCharacterId = Integer.parseInt(id_str);
        }
        else
            deleteCharacterButton.setEnabled(false);


        // set onClickLister to Add button
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Adding a new character
                if (selectedCharacterId == null) {

                    final String newEntry = editText.getText().toString().trim();

                    if (newEntry.isEmpty() || newEntry.length() == 0){
                        Toast.makeText(CharacterAddEdit.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                    }
                    else{

                        boolean insertData = myDB.addName(newEntry);

                        if(insertData){
                            Toast.makeText(CharacterAddEdit.this, "" + newEntry + " Successfully Inserted!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(CharacterAddEdit.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                // Updating an existing character
                else {

                    final String newName = editText.getText().toString().trim();
                    myDB.updateName(newName, selectedCharacterId);
                    Toast.makeText(CharacterAddEdit.this, "Updated " + newName, Toast.LENGTH_LONG).show();
                }

                // clear sharedpreferences
                MainActivity.clearSharedPreferences();
            }
        });


        // Set onClickLister to delete character button

        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        deleteCharacterButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                myDB.deleteCharacter(selectedCharacterId);
                Toast.makeText(CharacterAddEdit.this, "Deleted " + selectedCharacterName, Toast.LENGTH_LONG).show();
                MainActivity.clearSharedPreferences();
            }
        });
    }

    // listener for add/edit attack button to start addEditAttack activity
    public void addEditAttack(View view) {
        Intent intent = new Intent(this, AttackAddEdit.class);
        int id = myDB.getCharacterAttacksId(selectedCharacterId);
        intent.putExtra(ATTACKS_ID, id);
        startActivity(intent);
    }
}
