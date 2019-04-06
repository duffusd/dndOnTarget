package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * <h>DiceDatabaseHelper</h>
 *
 * DiceDatabaseHelper is a helper class that enables accessing and maintaining the database table
 * used for Die class
 *
 */
public class DiceDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:Dice";


    /**
     * Non-default constructor
     *
     * @param context
     */
    public DiceDatabaseHelper(Context context){

        super(context, DiceContract.getDbName(), null, DatabaseContract.version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DiceContract.getSqlCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DiceContract.getTableName());
        onCreate(db);

    }


    /**
     * Inserts Dice numbers (4, 6, 8, 10, 12, 20) in the table
     *
     * @exception SQLiteException
     * @author Atsuko Takanabe
     *
     */
    public void insertNumbers(){

        int diceNumbers[] = {4, 6, 8, 10, 12, 20};
        SQLiteDatabase db = getWritableDatabase();
        ContentValues numberContent = new ContentValues();

        for (int number : diceNumbers) {

            numberContent.put(DiceContract.getNumberColName(), number);

            try{

                db.insert(DiceContract.getTableName(), null, numberContent);

            }catch(SQLiteException e){

                e.printStackTrace();
                Log.e(ERROR_SQLite, "Inserting a dice number failed");
            }
        }

        db.close();
    }

    /**
     * Returns the number of sides of a die
     *
     * @param dieId
     * @return Number of the sides of a die
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public Integer getDieNumber(Integer dieId) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor data = null;
        Integer dieNumber = null;

        try {

            String sql = "SELECT " + DiceContract.getNumberColName() + " FROM " + DiceContract.getTableName()
                    + " WHERE " + DiceContract.getIdColName() + "=" + dieId;
            data = db.rawQuery(sql, null);

        } catch (SQLiteException e) {

            e.printStackTrace();
            Log.e(ERROR_SQLite, "getDieNumber() failed");
            
        }

        if (data != null) {
 
            while (data.moveToNext()) {

                dieNumber = Integer.parseInt(data.getString(data.getColumnIndex(DiceContract.getNumberColName())));
            }
        }

        return dieNumber;
    }

}
