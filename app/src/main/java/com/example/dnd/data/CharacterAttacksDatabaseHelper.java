package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.dnd.Attack;

import org.w3c.dom.CharacterData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <h>CharacterAttacksDatabaseHelper</h>
 *
 * CharacterAttacksDatabaseHelper is a helper class for accessing and maintaining the attributes of CharacterAttack
 * table
 */
public class CharacterAttacksDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:characterAttacks";
    Context _context;
    private volatile static CharacterAttacksDatabaseHelper dbHelper;

    /**
     * Non-default constructor
     * @param context
     */
    public CharacterAttacksDatabaseHelper(Context context){

        super(context, CharacterAttacksContract.getDbName(), null, DatabaseContract.version);
        _context = context;
    }


    /**
     * Returns the instance of the CharacterAttacksDatabaseHelper
     *
     * @param context
     * @return The instance of the CharacterAttacksDatabaseHelper
     * @author Atsuko Takanabe
     */
    public static CharacterAttacksDatabaseHelper getInstance(Context context){

        if(dbHelper == null){

            synchronized (CharacterAttacksDatabaseHelper.class){

                if(dbHelper == null){

                    dbHelper = new CharacterAttacksDatabaseHelper(context);
                }
            }
        }

        return  dbHelper;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CharacterAttacksContract.getSQLCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CharacterAttacksContract.getTableName());
        onCreate(db);
    }

    /**
     * Adds the character ID and the attack ID for that character in the database table
     *
     * @param characterId Character ID
     * @param attackId Attack ID that belongs to the character
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void addCharacterAttack(Integer characterId, Integer attackId){

        try {

            AttackDatabaseHelper attackDbHelper = AttackDatabaseHelper.getInstance(_context);
            SQLiteDatabase db = getReadableDatabase();

            boolean findAttack = attackDbHelper.findAttackById(attackId);

            if (findAttack){

                ContentValues values = new ContentValues();

                if(characterId != null){

                    values.put(CharacterAttacksContract.getCharacterIdColName(), characterId);
                }

                values.put(CharacterAttacksContract.getAttackIdColName(), attackId);
                db.insert(CharacterAttacksContract.getTableName(), null, values);
            }

            db.close();

        } catch (SQLiteException e) {

            e.printStackTrace();
            Log.e(ERROR_SQLite, "CharacterAttacks: Inserting a new character-attack record failed");
        }
    }

    /**
     * Returns all the attack IDs associated with the character ID
     *
     * @param characterId CharacterID of which attacks to enquire
     * @return List of attackIDs associated with the character
     * @author Atsuko Takanabe
     */
    public List<Integer> getAttackIdsByCharacterId(Integer characterId){

        List<Integer> attacksId = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("SELECT " + CharacterAttacksContract.getAttackIdColName() +
                " FROM " + CharacterAttacksContract.getTableName() +
                " WHERE " + CharacterAttacksContract.getCharacterIdColName() + "=" + characterId, null);

        while(data.getCount() != 0 && data.moveToNext()){

            attacksId.add(data.getInt(0));
        }

        db.close();

        return attacksId;
    }

    /**
     * Deletes the character and its attacks in the database table
     *
     * @param characterId Character ID to delete
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void deleteCharacter(int characterId) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(CharacterAttacksContract.getTableName(),
                    CharacterAttacksContract.getCharacterIdColName() + "=" + characterId,
                    null);
            db.close();

        } catch (SQLiteException e) {

            e.printStackTrace();
            Log.e(ERROR_SQLite, "Deleting a character failed");
        }
    }


    /**
     * Deletes the attack from the database table
     *
     * @param attackId AttackID to delete
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void deleteAttack(int attackId) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(CharacterAttacksContract.getTableName(),
                    CharacterAttacksContract.getAttackIdColName() + "=" + attackId,
                    null);
            db.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Deleting an attack failed");
        }
    }
}
