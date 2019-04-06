package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.dnd.Attack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * <h>AttackDatabaseHelper</h>
 *
 * AttackDatabaseHelper is a helper class that enables accessing the database table and updating the
 * attributes of an Attack object
 */
public class AttackDatabaseHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String ERROR_SQLite = "SQLite:Attacks";
    private Context _context;

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


    /**
     * Add a new attack to the table in the database.
     *
     * @param attackName Name of the new attack
     * @param hit Value of the hit modifier
     * @param damage Value of the damage modifier
     * @param diceId Dice ID
     * @param numOfDie Number of the die
     * @exception SQLiteException
     * @return AttackID for the new attack
     * @author Atsuko Takanabe
     */
    public int addAttack(String attackName, Integer hit, Integer damage, Integer diceId, Integer numOfDie){

        SQLiteDatabase db = getWritableDatabase();

        // verify that attackDiceId exists in the AttackDice table
        DiceDatabaseHelper diceDbHelper = new DiceDatabaseHelper(_context);
        //Boolean validDiceId = diceDbHelper.findDiceById(diceId);

        Long newId = null;

            // now insert the new attack to attackTable
            try {

                ContentValues newValue = new ContentValues();

                newValue.put(AttackContract.getAttackNameColName(), attackName);
                newValue.put(AttackContract.getHitModifierColName(), hit);
                newValue.put(AttackContract.getDamageModifierColName(), damage);
                newValue.put(AttackContract.getNumOfDieColName(), numOfDie);
                newValue.put(AttackContract.getDiceIdColName(), diceId);

                newId = db.insert(AttackContract.getTableName(), null, newValue);

                db.close();

            } catch (SQLiteException e){

                e.printStackTrace();
                Log.e(ERROR_SQLite, AttackContract.getTableName() + ": Adding a new attack failed");
                db.close();
            }

            return newId.intValue();
    }

    /**
     * Updates the name of the existing attack
     *
     * @param id Attack ID of the attack to update
     * @param newName New attack name
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateName(int id, String newName){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put(AttackContract.getAttackNameColName(), newName);

        try{

            db.update(AttackContract.getTableName(), newValue,
                    AttackContract.getIdColName() + "=" + id, null);
            db.close();

        } catch(SQLiteException e){

            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attack table: Updating an attack name failed");
        }
    }

    /**
     * Updates the hit modifier value of the existing attack
     *
     * @param id Attack ID of the attack to update
     * @param newNumber New hit modifier value
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateHitModifier(int id, int newNumber){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues  newValue = new ContentValues();
        newValue.put(AttackContract.getHitModifierColName(), newNumber);

        try {

            db.update(AttackContract.getTableName(), newValue,
                    AttackContract.getIdColName() + "=" + id, null);
            db.close();

        } catch(SQLiteException e){

            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attacks table: Updating a hit modifier failed");
        }
    }


    /**
     * Updates the value of the damage modifier of the existing attack
     *
     * @param id Attack ID of an attack to udpate
     * @param newNumber New damage modifier value
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
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
            Log.e(ERROR_SQLite, "Updating a damage modifier failed");
        }
    }


    /**
     * Updates the number of die for the existing attack
     *
     * @param attackId Attack ID of the attack to update
     * @param newNumber New number of dice value
     * @exception  SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateNumOfDie(int attackId, int newNumber){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues newValue = new ContentValues();
            newValue.put(AttackContract.getNumOfDieColName(), newNumber);
            db.update(AttackContract.getTableName(), newValue,
                    AttackContract.getIdColName() + "=" + attackId, null);
            db.close();

        } catch(SQLiteException e){

            e.printStackTrace();
            Log.e(ERROR_SQLite, "Updating the number of die failed");
        }

    }
  
  
    /**
     * Updates the diceID of the existing attack
     *
     * @param id Attack ID of the attack to update
     * @param newDiceId New diceId value
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateDiceId(int id, int newDiceId){

        try{

            SQLiteDatabase db = getWritableDatabase();
            ContentValues newValue = new ContentValues();
            newValue.put(AttackContract.getDiceIdColName(), newDiceId);
            db.update(AttackContract.getTableName(), newValue, AttackContract.getIdColName() + "=" + id, null);
            db.close();

        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(ERROR_SQLite, "Attacks table: Updating a diceID failed");
        }

    }
  
    /**
     * Deletes the existing attack from the database table
     *
     * @param id Attack ID of the attack to delete
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
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


    /**
     * Searches for the attack by attack ID and returns true if it is found; false otherwise
     *
     * @param id Attack ID to find
     * @return True if attack is found; false otherwise
     * @author Atsuko Takanabe
     * @exception SQLiteException
     */
    public Boolean findAttackById(int id) {

        Cursor c = null;
        SQLiteDatabase db = null;
        String sqlSelectAttackId =
                "SELECT " + AttackContract.getIdColName() + " FROM " + AttackContract.getTableName() +
                        " WHERE " + AttackContract.getIdColName() + "=" + id;
        try {

            db = getWritableDatabase();
            c = db.rawQuery(sqlSelectAttackId, null);

        }catch(SQLiteException e) {

            e.printStackTrace();
            Log.e(ERROR_SQLite, "findAttackById(): Something went wrong");
        }

        if (c != null && c.getCount() == 1) {
                db.close();
                return true;
        }
        else{
            db.close();
            return false;
        }
    }


    /**
     * Returns all the attributes of the attack in the HashMap where Key is the
     * column name and Value is the attribute's value
     *
     * @param attackId Attack ID of the attack to find
     * @return Attack name, hit modifier, damage modifier, number of dice, dice ID
     * @author Atsuko Takanabe
     */
    public Map<String, String> getAttackDetails(Integer attackId){

        Map<String, String> row = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + AttackContract.getTableName() +
                " WHERE " + AttackContract.getIdColName() + "=" + attackId, null);

        if(data.getCount() == 0){

            Log.d(ERROR_SQLite, "There should be only one attack returned");
            Log.d(ERROR_SQLite, String.format("Number of Attack returned: %d", data.getCount()));
        }

        while(data.moveToNext()){

            for (int i = 0; i < data.getColumnCount(); i++){
                row.put(data.getColumnName(i), data.getString(i));
            }

        }

        db.close();
        return row;
    }


    public Cursor getAttackContents(List<Integer> attackIds){
        // converts int list into a string
        String listString = attackIds.toString();
        String idList = listString.toString();
        // changes it from a [] to a comma separated list
        String csv = idList.substring(1, idList.length() - 1).replace(", ", ",");
        Log.e("DB", "get Attack contents is running " + csv);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = (Cursor) db.rawQuery("SELECT * FROM " + AttackContract.getTableName() + " WHERE " + AttackContract.getIdColName() + " in (" + csv + ")", null);
        return data;
    }

}
