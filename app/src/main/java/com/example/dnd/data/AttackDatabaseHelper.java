package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class AttackDatabaseHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String ERROR_SQLite = "SQLite:Attacks";

    // non-default constructor
    public AttackDatabaseHelper(Context context){
        super(context, AttackContract.getDbName(), null, DatabaseContract.version);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(AttackContract.getSQLCreateTable());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AttackContract.getTableName());
        onCreate(db);
    }




    public void addModifiers(int hit, int damage){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues newValue = new ContentValues();
            newValue.put(AttackContract.getHitModifierColName(), hit);
            newValue.put(AttackContract.getDamageModifierColName(), damage);
            db.insert(AttackContract.getTableName(), null, newValue);
            db.close();

        } catch (SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attacks table: Adding hit and damage modifiers failed");

        }
    }


    public void updateHitModifier(int id, int newNumber){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues  newValue = new ContentValues();
            newValue.put(AttackContract.getHitModifierColName(), newNumber);
            db.update(AttackContract.getTableName(), newValue,
                    AttackContract.getIdColName() + "=" + id, null);
            db.close();

        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attacks table: Updating a hit modifier failed");
        }
    }

    public void updateDamageModifier(int id, int newNumber){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues  newValue = new ContentValues();
            newValue.put(AttackContract.getDamageModifierColName(), newNumber);
            db.update(AttackContract.getTableName(), newValue,
                    AttackContract.getIdColName() + "=" + id, null);
            db.close();

        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attacks table: Updating a damage modifier failed");
        }
    }

    public void deleteAttack(int id) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(AttackContract.getTableName(),
                    AttackContract.getIdColName() + "=" + id,
                    null);
            db.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Deleting an attack failed");
        }
    }

    public Boolean findAttackById(int id) {


        String sqlSelectAttackId =
                "SELECT " + AttackContract.getIdColName() + " FROM " + AttackContract.getTableName() +
                        " WHERE " + AttackContract.getIdColName() + "=" + id;
        try {

            SQLiteDatabase db = getWritableDatabase();
            Cursor c = db.rawQuery(sqlSelectAttackId, null);
            db.close();

            if (c != null) {
                return true;
            }
            else
                return false;

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "findAttackById(): Couldn't find a character");
            return false;
        }
    }
}
