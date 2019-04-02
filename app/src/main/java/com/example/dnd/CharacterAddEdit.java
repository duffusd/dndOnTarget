package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import com.example.dnd.data.AttackDatabaseHelper;
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
    public static Attack selectedAttack;
    //private Intent attackIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);

        // get the buttons
        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        addEditAttackButton = findViewById(R.id.addEditAttackButton);
        btnAdd = findViewById(R.id.saveCharacterButton);
        editText = findViewById(R.id.characterNameEditText);
        tag = "CharacterAddEdit";


        /* If the user has chosen to edit the existing character, fill the character name field
         with the selected character's name */

        if (MainActivity.getCharacter().getName() != null) {
            editText.setText(MainActivity.getCharacter().getName());
        } else {

            // if no character has been chosen to edit, that means adding a new character
            // User shouldn't be able to click "delete character" and "add/edit attack" buttons
            deleteCharacterButton.setEnabled(false);
            addEditAttackButton.setEnabled(false);
        }

    }

    @Override
    protected void onStart() {

        super.onStart();

        // clear the attack every time the user comes to this activity
        MainActivity.getAttack().clear();

        // Display all the attacks that belong to the character
        displayAllAttacks();

        // set onClickLister to Add button
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = editText.getText().toString().trim();

                // validate the character name
                try{

                    MainActivity.getCharacter().validateCharacterName(nameStr);

                }catch(Exception e){

                    Toast.makeText(CharacterAddEdit.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(tag, e.getMessage());
                    return;
                }


                // Adding a new character
                if (MainActivity.getCharacter().getId() == null) {

                        Integer newId = MainActivity.getCharacter().addNewCharacter(nameStr);

                        if (newId == -1) {

                            //MainActivity.getCharacter().addNewCharacter(name);
                            Toast.makeText(CharacterAddEdit.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();

                        } else {

                            MainActivity.getCharacter().setId(newId);
                            MainActivity.getCharacter().setName(nameStr);

                            Toast.makeText(CharacterAddEdit.this, "" + nameStr + " Successfully Inserted!", Toast.LENGTH_LONG).show();
                            addEditAttackButton.setEnabled(true);
                            deleteCharacterButton.setEnabled(true);
                        }
                }

                // Updating an existing character's name
                else {

                    MainActivity.getCharacter().updateCharacter(nameStr);
                    Toast.makeText(CharacterAddEdit.this, String.format("Updated %s", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();
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


    private void displayAllAttacks(){

        // only display attacks list if the character was selected to edit in the previous step
        if (MainActivity.getCharacter().getId() != null) {

            MainActivity.getCharacter().generateAttacksForCharacter();

            ListView attackListView = findViewById(R.id.attackListView);
            attackListView.setSelector(R.drawable.ic_launcher_background);
            AttacksListAdapter adapter = new AttacksListAdapter(this, R.layout.attack_list_adapter, MainActivity.getCharacter().getAttacks());

            LayoutInflater inflater = getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.attacks_list_header, attackListView, false);
            attackListView.addHeaderView(header, null, false);

            attackListView.setAdapter(adapter);


            // set onClick lister for attackListView
            attackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    attackListView.setSelector(R.drawable.ic_launcher_background);

                    // get the clicked attack
                    selectedAttack = new Attack((Attack) parent.getItemAtPosition(position));

                    // set the main attack to the clicked attack
                    MainActivity.setAttack(selectedAttack);
                }
            });

        }
    }

}
