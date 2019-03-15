package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CharacterAddEdit extends AppCompatActivity {

    public static final String ATTACKS_ID = "com.example.dnd.ATTACKS_ID";

    //private CharacterDatabaseHelper myDB;
    private Button btnAdd;
    private Button deleteCharacterButton;
    private Button addEditAttackButton;
    private EditText editText;
    private String tag;
    //private Intent attackIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);

        // get the buttons
        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        addEditAttackButton = findViewById(R.id.addEditAttackButton);
        btnAdd = findViewById(R.id.saveCharacterButton);
        editText =  findViewById(R.id.characterNameEditText);
        tag = "CharacterAddEdit";


        /** If the user chose to edit the existing character, populate this field with that
         * character's name and get its id * */

        if(MainActivity.getCharacter().getName() != null){
            editText.setText(MainActivity.getCharacter().getName());
        }
        else {
            deleteCharacterButton.setEnabled(false);
            addEditAttackButton.setEnabled(false);
        }
      

        /***** display attacks for a character ********/

        // clear attacks of the character object
        MainActivity.getCharacter().clearAttacks();

        // Get attackIds for the character
        List<Integer> attackIds = MainActivity.getCharacter().getAttackIdsForCharacter();

        // Create an attack object for each attackIds, then add it to the attack list of the character object
        if(attackIds.size() > 0){
            for (int i = 0; i < attackIds.size(); i++){
                System.out.println("HERE: " + attackIds.get(i));
                Attack attack = new Attack(this);
                attack.setAttack(attackIds.get(i));
                MainActivity.getCharacter().addAttack(attack);
            }
        }

        ListView attackListView = findViewById(R.id.attackListView);
        List<String> attackNames = new ArrayList<>();
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attackNames);

        if (MainActivity.getCharacter().getAttacks().size() == 0){
            Toast.makeText(CharacterAddEdit.this, "No attacks available", Toast.LENGTH_LONG).show();
        }
        else{
            for (Attack attack: MainActivity.getCharacter().getAttacks()){
                attackNames.add(attack.getName());
                attackListView.setAdapter(listAdapter);
            }
        }

        // Set OnItemClickLister to attacks' listView
        attackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get the name of attack from the clicked item
                String attackName = (String)parent.getItemAtPosition(position);

                // create an attack object

                for (Attack attack : MainActivity.getCharacter().getAttacks()){
                    if(attack.getName() == attackName){
                        MainActivity.setAttack(attack);
                    }
                }

            }
        });


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

                    }
                }
                // Updating an existing character
                else {

                    final String newName = editText.getText().toString().trim();
                    MainActivity.getCharacter().updateCharacter(newName);
                    Toast.makeText(CharacterAddEdit.this, String.format("Updated %s", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

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

    /*
    // listener for add/edit attack button to start addEditAttack activity
    public void addEditAttack(View view) {
        setContentView(R.layout.activity_attack_add_edit);
        Intent intent = new Intent(this, AttackAddEdit.class);
        int id = myDB.getCharacterAttacksId(selectedCharacterId);
        intent.putExtra(ATTACKS_ID, id);
        startActivity(intent);
    }
    */
}
