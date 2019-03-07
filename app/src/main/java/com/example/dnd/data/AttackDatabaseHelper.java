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
    private Context _context;

    // non-default constructor
    public AttackDatabaseHelper(Context context){
        super(context, AttackContract.getDbName(), null, DatabaseContract.version);
        SQLiteDatabase db = getWritableDatabase();
        _context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AttackContract.getSQLCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AttackContract.getTableName());
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    public void addAttack(String attackName, int hit, int damage, int diceId){

        SQLiteDatabase db = getWritableDatabase();

        // verify that attackDiceId exists in the AttackDice table
        DiceDatabaseHelper diceDbHelper = new DiceDatabaseHelper(_context);
        Boolean validDiceId = diceDbHelper.findDiceById(diceId);

            // now insert the new attack to attackTable
            try {

                ContentValues newValue = new ContentValues();

                newValue.put(AttackContract.getAttackNameColName(), attackName);
                newValue.put(AttackContract.getHitModifierColName(), hit);
                newValue.put(AttackContract.getDamageModifierColName(), damage);

                // inserting -1 if diceId is invalid
                if(validDiceId) {
                    newValue.put(AttackContract.getDiceIdColName(), diceId);
                }
                else {
                    newValue.put(AttackContract.getDiceIdColName(), -1);
                }

                db.insert(AttackContract.getTableName(), null, newValue);
                db.close();

            } catch (SQLiteException e){
                e.printStackTrace();
                Log.e(ERROR_SQLite, AttackContract.getTableName() + ": Adding a new attack failed");
                db.close();
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

            if (c != null && c.getCount() == 1) {
                db.close();
                return true;
            }
            else
                db.close();
                return false;

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "findAttackById(): Couldn't find a character");
            return false;
        }
    }
}
