package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.dnd.Attack;

import org.w3c.dom.CharacterData;

public class CharacterAttacksDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:characterAttacks";
    Context _context;

    public CharacterAttacksDatabaseHelper(Context context){
        super(context, CharacterAttacksContract.getDbName(), null, DatabaseContract.version);
        _context = context;
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

    public void addCharacterAttack(Integer characterId, Integer attackId){

        try {

            CharacterDatabaseHelper characterDbhelper = new CharacterDatabaseHelper(_context);
            AttackDatabaseHelper attackDbHelper = new AttackDatabaseHelper(_context);
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

    public void deleteCharacterAttack(int characterId) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(CharacterAttacksContract.getTableName(),
                    CharacterAttacksContract.getCharacterIdColName() + "=" + characterId,
                    null);
            db.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Deleting a character-attack failed");
        }
    }
}
