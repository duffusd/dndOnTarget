package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Button addEditAttackButton;
    private EditText editText;
    private String selectedCharacterName;
    private Integer selectedCharacterId;
    private ListView listAttack;
    //private Intent attackIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);
        myDB = new CharacterDatabaseHelper(this);

        //selectedCharacterName = MainActivity.sharedPreferences.getString(MainActivity.SharedPrefCharacterName, null);
        //attackIntent = new Intent(this, AttackAddEdit.class);

        // get the buttons
        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        addEditAttackButton = findViewById(R.id.addEditAttackButton);
        listAttack = findViewById(R.id.attackListView);
        btnAdd = findViewById(R.id.saveCharacterButton);
        editText =  findViewById(R.id.characterNameEditText);


        /* If the user chose to edit the existing character, populate this field with that character's name
          * and get its id * */

        if(MainActivity.getCharacter().getName() != null){
            editText.setText(MainActivity.getCharacter().getName());
            //String id_str = myDB.getCharacterIdByName(selectedCharacterName);
            //selectedCharacterId = Integer.parseInt(id_str);
        }
        else {
            deleteCharacterButton.setEnabled(false);

        }


        // set onClickLister to Add button
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Adding a new character
                if (MainActivity.getCharacter().getId() == null) {

                    final String newName = editText.getText().toString().trim();

                    if (newName.isEmpty() || newName.length() == 0){
                        Toast.makeText(CharacterAddEdit.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                    }
                    else{

                        MainActivity.getCharacter().addNewCharacter(newName);

                        if(MainActivity.getCharacter().getId() == -1){
                            Toast.makeText(CharacterAddEdit.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(CharacterAddEdit.this, "" + newName + " Successfully Inserted!", Toast.LENGTH_LONG).show();
                        }

                        // Save the character ID and name in the shared preference
                        //SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                        //editor.putString(MainActivity.SharedPrefCharacterId, newId.toString());
                        //editor.putString(MainActivity.SharedPrefCharacterName, newEntry);
                        //editor.commit();
                    }
                }
                // Updating an existing character
                else {

                    final String newName = editText.getText().toString().trim();
                    myDB.updateName(newName, selectedCharacterId);
                    Toast.makeText(CharacterAddEdit.this, "Updated " + newName, Toast.LENGTH_LONG).show();

                    // clear sharedpreferences
                    //MainActivity.clearSharedPreferences();
                    MainActivity.getCharacter().clearCharacter();

                }
            }
        });


        // Set onClickLister to delete character button
        deleteCharacterButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // call deleteCharacter method in Character to delete the character
                MainActivity.getCharacter().deleteCharacter();

                Toast.makeText(CharacterAddEdit.this, String.format("Deleted %s", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

                //MainActivity.clearSharedPreferences();
                MainActivity.getCharacter().clearCharacter();
            }
        });

        // Set onClickLister to add/edit attack button
        addEditAttackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attackIntent = new Intent(CharacterAddEdit.this, AttackAddEdit.class);
                startActivity(attackIntent);
            }
        });



    }

    // listener for add/edit attack button to start addEditAttack activity
    public void addEditAttack(View view) {
        setContentView(R.layout.activity_attack_add_edit);
        Intent intent = new Intent(this, AttackAddEdit.class);
        int id = myDB.getCharacterAttacksId(selectedCharacterId);
        intent.putExtra(ATTACKS_ID, id);
        startActivity(intent);
    }

}
