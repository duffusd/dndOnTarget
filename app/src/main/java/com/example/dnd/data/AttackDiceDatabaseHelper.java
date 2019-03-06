package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AttackDiceDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:AttackDice";

    public AttackDiceDatabaseHelper(Context context){
        super(context, AttackDiceContract.getDbName(), null, DatabaseContract.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AttackDiceContract.getSqlCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AttackDiceContract.getTableName());
        onCreate(db);
    }


    public void addAttackDice(String attackDiceString){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put(AttackDiceContract.getDiceStringColName(), attackDiceString);

        try {
            db.insert(AttackDiceContract.getTableName(), null, newValue);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, AttackDiceContract.getTableName() + ": Adding a new attack-dice failed");
        }

        db.close();

    }

    public void updateAttackDice(int id, String newAttackDice){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues  newValue = new ContentValues();
        newValue.put(AttackDiceContract.getDiceStringColName(), newAttackDice);

        try {
            db.update(AttackDiceContract.getTableName(), newValue,
                    AttackDiceContract.getIdColName() + "=" + id, null);
        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Updating the attack-dice string failed");
        }

        db.close();
    }

    public void deleteAttackDice(int id) {

        SQLiteDatabase db = getWritableDatabase();
        try {

            db.delete(AttackDiceContract.getTableName(),
                    AttackDiceContract.getIdColName() + "=" + id, null);
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Deleting an attack-dice failed");
        }

        db.close();
    }


    public boolean findAttackDiceById(int id){

        SQLiteDatabase db = getReadableDatabase();
        String selectAttackDiceByAttackDice =
                "SELECT " + AttackDiceContract.getIdColName() + " FROM " + AttackDiceContract.getTableName() +
                        " WHERE " + AttackDiceContract.getIdColName() + "=" + id;

        try {

            Cursor c = db.rawQuery(selectAttackDiceByAttackDice, null);

            if (c != null && c.getCount() == 1) {
                db.close();
                return true;
            }
            else
                db.close();
                return false;

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Can't find an attack-dice");
            return false;
        }
    }

}
