package com.example.dnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;

public class CharacterAddEdit extends AppCompatActivity {

    private Button createAttackButton;
    private Button deleteCharacterButton;
    private Button saveCharacterButton;
    private EditText characterNameText;
    private ListView attackListView;
    private String tag;
    public static Attack selectedAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);

        // get the buttons
        deleteCharacterButton = findViewById(R.id.deleteCharacterButton);
        saveCharacterButton = findViewById(R.id.saveCharacterButton);
        createAttackButton = findViewById(R.id.addAttackButton);
        characterNameText = findViewById(R.id.characterNameEditText);
        tag = "CharacterAddEdit";


        /* If the user has chosen to edit the existing character, fill the character name field
         with the selected character's name */

        if (MainActivity.getCharacter().getName() != null) {

            characterNameText.setText(MainActivity.getCharacter().getName());

        } else{
            // if no character has been chosen to edit, that means adding a new character
            // User shouldn't be able to delete the character or create the attack
            deleteCharacterButton.setEnabled(false);
            createAttackButton.setEnabled(false);
        }

        // clear the attack every time the user comes to this activity
        MainActivity.getAttack().clear();

        // Display all the attacks that belong to the character
        MainActivity.getCharacter().generateAttacksForCharacter();

        attackListView = findViewById(R.id.attackListView);
        attackListView.setSelector(R.drawable.ic_launcher_background);
        AttacksListAdapter adapter = new AttacksListAdapter(this, R.layout.attack_list_adapter, MainActivity.getCharacter().getAttacks());

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.attacks_list_header, attackListView, false);
        attackListView.addHeaderView(header, null, false);
        attackListView.setAdapter(adapter);

        // for setting up context menu
        registerForContextMenu(attackListView);


    }

    @Override
    protected void onStart() {

        super.onStart();

        // set onClickLister to Add button
        saveCharacterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = characterNameText.getText().toString().trim();

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

                            Toast.makeText(CharacterAddEdit.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();

                        } else {

                            MainActivity.getCharacter().setId(newId);
                            MainActivity.getCharacter().setName(nameStr);

                            Toast.makeText(CharacterAddEdit.this, "" + nameStr + " Successfully Inserted!", Toast.LENGTH_LONG).show();
                            saveCharacterButton.setEnabled(true);
                            deleteCharacterButton.setEnabled(true);
                            createAttackButton.setEnabled(true);
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

        // Set onClickLister to create attack button
        createAttackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CharacterAddEdit.this, AttackAddEdit.class);
                startActivity(intent);
            }
        });


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

        //characterListView.setLongClickable(true);
        attackListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                selectedAttack = new Attack((Attack) parent.getItemAtPosition(position));
                MainActivity.setAttack(selectedAttack);

                return false;
            }
        });

        attackListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                String[] menuItems = getResources().getStringArray(R.array.options);

                for(int i = 0; i < menuItems.length; i++) {

                    menu.add(0, i, i, menuItems[i]);

                }
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Get the name of the selected operation to perform

        String menuItemName = null;

        if(item.getGroupId() == 0){

            menuItemName = getResources().getStringArray(R.array.options)[item.getItemId()];

            switch (menuItemName){

                case "Edit":
                    Intent intent = new Intent(CharacterAddEdit.this, AttackAddEdit.class);
                    startActivity(intent);
                    break;

                case "Delete":
                    Toast.makeText(this, String.format("Deleted %s", MainActivity.getAttack().getName()), Toast.LENGTH_LONG).show();
                    MainActivity.getCharacter().removeAttack(MainActivity.getAttack());
                    MainActivity.getAttack().deleteAttack(MainActivity.getAttack().getId());
                    MainActivity.getAttack().clear();
                    recreate();
                    break;

            }

        }

        return true;
    }



}
