package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dnd.Character;

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


    public boolean addName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CharacterContract.getNameColName(), name);
        long result = -1; // initiating variable with -1

        try {
            result = db.insert(CharacterContract.getTableName(), null, contentValues);
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Inserting a new character name failed");
        }

        if(result == -1){
            return false;
        }
        else
            return true;
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

    // get the id of the CharacterAttacks column with character id
    public int getCharacterAttacksId(int id) {

        SQLiteDatabase db = getReadableDatabase();
        String sqlSelectCharacterAttackId =
                "SELECT " + CharacterContract.getCharacterAttacksIdColName()
                        + " FROM " + CharacterContract.getTableName()
                        + " WHERE " + CharacterContract.getCharacterAttacksIdColName()
                        + "=" + id;

        Cursor c = null;

        try {
            c = db.rawQuery(sqlSelectCharacterAttackId, null);

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "getCharacterAttacksId() FAILED");
            return -1;
        }

            if (c != null) {
                c.moveToFirst();
                int idCharAtks = c.getInt(c.getColumnIndex(CharacterContract.getCharacterAttacksIdColName()));
                return idCharAtks;
            } else {
                return -1;
            }
    }

    public String getCharacterIdByName(String name){

        String sqlSelectCharacterId =
                "SELECT " + CharacterContract.getIdColName() +
                        " FROM " + CharacterContract.getTableName() +
                        " WHERE " + CharacterContract.getNameColName() + "=" + "'" + name + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;

        try{
            c = db.rawQuery(sqlSelectCharacterId, null);
        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "getCharacterIdByName failed");
        }

        if (c != null){
            c.moveToFirst();
            Integer id = c.getInt(c.getColumnIndex(CharacterContract.getIdColName()));
            return id.toString();
        }
        else
            return "";

    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + CharacterContract.getTableName(), null);
        return data;
    }

    // Data-seeds method
    public void addCharacterNames(){

        SQLiteDatabase db = getWritableDatabase();
        String characters[] = {"Claire", "Shaun", "Bob", "Leah"};

        for (String character : characters){

            ContentValues value = new ContentValues();
            value.put(CharacterContract.getNameColName(), character);
            db.insert(CharacterContract.getTableName(), null, value);
        }

        db.close();

    }
}
