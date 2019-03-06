package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CharacterDatabaseHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String ERROR_SQLite = "SQLite:Characters";


    public CharacterDatabaseHelper(Context context) {
        super(context, CharacterContract.getDbName(), null, DatabaseContract.version);
        SQLiteDatabase db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CharacterContract.getSQLCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CharacterContract.getTableName());
        onCreate(db);
    }


    public void addName(String name){

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CharacterContract.getNameColName(), name);
            db.insert(CharacterContract.getTableName(), null, values);
            db.close();

        } catch (SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Adding a new character failed");
        }
    }



    public void updateName(String newName, int id){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues  newValue = new ContentValues();
            newValue.put(CharacterContract.getNameColName(), newName);
            db.update(CharacterContract.getTableName(), newValue,
                    CharacterContract.getIdColName() + "=" + id, null);
            db.close();

        } catch (SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Chracters table: Updating a character name failed");

        }
    }

    public void deleteCharacter(int id){

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(CharacterContract.getTableName(),
                    CharacterContract.getIdColName() + "=" + id,
                    null);
            db.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "deleteCharacter(): Deleting a character failed");
        }

    }

    public boolean findCharacter(int id){

        try{

            SQLiteDatabase db = getWritableDatabase();

            String sqlSelectCharacterId =
                    "SELECT " + CharacterContract.getIdColName() + " FROM " + CharacterContract.getTableName() +
                    " WHERE " + CharacterContract.getIdColName() + "=" + id;

            Cursor c = db.rawQuery(sqlSelectCharacterId, null);

            if (c != null && c.getCount() == 1) {
                db.close();
                return true;
            }
            else
                db.close();
                return false;

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "findCharacter(): Couldn't find a character");
            return false;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + CharacterContract.getTableName(), null);
        return data;
    }
}
