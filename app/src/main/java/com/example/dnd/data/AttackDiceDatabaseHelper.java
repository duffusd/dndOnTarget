package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *<h>AttackDiceDatabaseHelper</h?
 * AttackDiceDatabaseHelper is a helper class which enables accessing and maintaining the AttackDice
 * table
 *
 * @author Atsuko Takanabe
 */
public class AttackDiceDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:AttackDice";
    Context _context;

    /**
     * Non-default constructor
     * @param context
     */
    public AttackDiceDatabaseHelper(Context context){
        super(context, AttackDiceContract.getDbName(), null, DatabaseContract.version);
        _context = context;
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


    /**
     * Adds AttackID and DiceID to the database table
     *
     * @param attackId
     * @param diceId
     * @exception SQLiteException
     */
    public void addAttackDice(int attackId, int diceId){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValue = new ContentValues();

        // Validate diceId
        if (diceId >= 1 && diceId <= 6){
            newValue.put(AttackDiceContract.getDiceIdColName(), diceId);
        }
        else{
            Log.e(ERROR_SQLite, "Invalid diceId");
            return;
        }

        // Validate attackId
        AttackDatabaseHelper attackDbHelper = new AttackDatabaseHelper(_context);
        Boolean validAttackId = attackDbHelper.findAttackById(attackId);

        if(validAttackId){
            newValue.put(AttackDiceContract.getAttackIdColName(), attackId);
        }
        else{
            Log.e(ERROR_SQLite, "Invalid attackId");
        }


        // Now ready to insert

        try {

            db.insert(AttackDiceContract.getTableName(), null, newValue);

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(ERROR_SQLite, AttackDiceContract.getTableName() + ": Adding a new attack-dice failed");
        }

        db.close();

    }

    /**
     * Deletes the AttackID and DiceID from the database table
     *
     * @param attackId
     * @param diceId
     * @exception SQLiteException
     */
    public void deleteAttackDice(int attackId, int diceId) {

        //DiceDatabaseHelper diceDbHelper = new DiceDatabaseHelper(_context);
        AttackDatabaseHelper attackDbHelper = new AttackDatabaseHelper(_context);

        //Boolean diceIdExists = diceDbHelper.findDiceById(diceId);
        Boolean attackIdExists = attackDbHelper.findAttackById(attackId);

        SQLiteDatabase db = getWritableDatabase();

        if (attackIdExists){
            try {

                db.delete(AttackDiceContract.getTableName(),
                        AttackDiceContract.getAttackIdColName() + " =" + attackId + " AND " +
                        AttackDiceContract.getDiceIdColName() + " = " + diceId,
                        null);
            }catch(SQLiteException e){
                e.printStackTrace();
                Log.e(ERROR_SQLite, "Deleting an attack-dice failed");
            }
        }

        db.close();
    }
}
