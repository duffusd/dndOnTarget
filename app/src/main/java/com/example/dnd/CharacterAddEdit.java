package com.example.dnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import com.example.dnd.data.CharacterDatabaseHelper;

public class CharacterAddEdit extends AppCompatActivity {
    CharacterDatabaseHelper myDB;
    public Button btnAdd;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_add_edit);
        myDB = new CharacterDatabaseHelper(this);

        editText =  findViewById(R.id.characterNameEditText);
        String selectedCharacterName = MainActivity.sharedPreferences.getString(MainActivity.SharedPrefCharacterName, "");
        if(!selectedCharacterName.isEmpty()){
            editText.setText(selectedCharacterName);
        }

        btnAdd = findViewById(R.id.saveCharacterButton);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry = editText.getText().toString().trim();

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

                // clear sharedpreferences
                MainActivity.clearSharedPreferences();
            }
        });

    }
}
