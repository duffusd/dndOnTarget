package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "characters.db";
    private static final int DATABASE_VERSION = 3;


    //SQL to create tables
    private static final String TABLE_CHARACTERS_CREATE=
            "CREATE TABLE " + CharacterContract.CharacterEntry.table_name + " (" +
                    CharacterContract.CharacterEntry._id + " INTEGER PRIMARY KEY, " +
                    CharacterContract.CharacterEntry.name_col + " TEXT, " +
                    CharacterContract.CharacterEntry.characterAttacksId_col + " INTEGER" +
                    ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db;
        db = getWritableDatabase(); // getWritableDatabase function calls onCreate/onUpgrade methods

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CHARACTERS_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterEntry.table_name);
        onCreate(db);
    }


    public boolean addName(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CharacterContract.CharacterEntry.name_col, name);
        db.insert(CharacterContract.CharacterEntry.table_name, null, values);
        db.close();

        return true;
    }
}
