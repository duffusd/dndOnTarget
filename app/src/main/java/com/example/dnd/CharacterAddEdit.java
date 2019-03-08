package com.example.dnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import com.example.dnd.data.CharacterDatabaseHelper;

public class CharacterAddEdit extends AppCompatActivity implements View.OnClickListener {

    CharacterDatabaseHelper myDB;
    Button addEditButton;
    EditText nameEditTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view
        setContentView(R.layout.activity_character_add_edit);

        // Set the Add/Edit button
        addEditButton = this.findViewById(R.id.addEditCharacterButton);
        addEditButton.setOnClickListener(this);

        // Set the Name fieldname
        nameEditTextField = this.findViewById(R.id.characterNameEditText);

        // Initiate the db helper
        myDB = new CharacterDatabaseHelper(this);

    }

    public void addName(String newEntry) {

        boolean insertData = myDB.addName(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEditCharacterButton:
                String newCharacterName = nameEditTextField.getText().toString();
                addName(newCharacterName);
                break;

        }
    }
}
