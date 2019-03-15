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
