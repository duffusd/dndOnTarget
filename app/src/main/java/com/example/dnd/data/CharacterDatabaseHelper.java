package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.w3c.dom.CharacterData;

/**
 * <h>CharacterDatabaseHelper</h>
 *
 * CharacterDatabaseHelper is a helper class which enables accessing and maintaining the database
 * table used for storing character information
 */
public class CharacterDatabaseHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String ERROR_SQLite = "SQLite:Characters";
    private volatile static CharacterDatabaseHelper dbHelper;


    /**
     * Non-default constructor
     * @param context
     */
    public CharacterDatabaseHelper(Context context) {
        super(context, CharacterContract.getDbName(), null, DatabaseContract.version);
        SQLiteDatabase db = getWritableDatabase();
    }



    public static CharacterDatabaseHelper getInstance(Context context){

        if(dbHelper == null){

            synchronized (CharacterDatabaseHelper.class){

                if(dbHelper == null){

                    dbHelper = new CharacterDatabaseHelper(context);
                }
            }
        }

        return  dbHelper;
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


    /**
     * Adds a new character to the database table.
     *
     * @param name Name of the new character
     * @return New CharacterID. If the procedure fails, it returns -1
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public Integer addName(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CharacterContract.getNameColName(), name);
        Long result = null;

        try {
            // if the insert goes wrong, it returns -1. Otherwise, it returns the new character ID
            result = db.insert(CharacterContract.getTableName(), null, contentValues);

        }catch(SQLiteException e){

            e.printStackTrace();
            Log.e(ERROR_SQLite, "Inserting a new character name failed");
        }

       return result.intValue();
    }


    /**
     * Updates the name of the existing character
     *
     * @param newName New character name to update with
     * @param id CharacterID of which character name to update
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
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


    /**
     * Deletes the specific character from the database table
     *
     * @param id CharacterID to delete
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
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

    public Boolean findCharacterByName(String name){

        String sqlFindCharacterByName =
                "SELECT " + CharacterContract.getNameColName() +
                        " FROM " + CharacterContract.getTableName() +
                        " WHERE " + CharacterContract.getNameColName() + "=" + "'" + name + "'";

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = null;

        try{

            c = db.rawQuery(sqlFindCharacterByName, null);

        }catch(SQLiteException e){

            e.printStackTrace();
            Log.e(ERROR_SQLite, "findCharacterByName Failed");

        }

        if(c.getCount() == 1){

            return true;

        }else{

            return false;

        }
    }



    /**
     * Returns the CharacterID of the character
     *
     * @param name Name of the character of which ID to enquire
     * @return CharacterID
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public Integer getCharacterIdByName(String name){

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
            return id;

        } else
            return null;

    }

    /**
     * Returns all the characters in the database table
     *
     * @return Cursor that contains the data from the SQL query
     * @author Aaron Lee
     */
    public Cursor getListContents(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + CharacterContract.getTableName(), null);
        return data;

    }

}
