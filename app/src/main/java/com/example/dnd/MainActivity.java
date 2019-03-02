package com.example.dnd;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dnd.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);
        //dbHelper.addName("test character");
        //dbHelper.updateName("Eric", 1);
        //dbHelper.deleteCharacter(2);

        setContentView(R.layout.activity_main);
    }
}
