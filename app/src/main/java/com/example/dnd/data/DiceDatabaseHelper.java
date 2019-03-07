package com.example.dnd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiceDatabaseHelper extends SQLiteOpenHelper {

    private static final String ERROR_SQLite = "SQLite:Dice";

    public DiceDatabaseHelper(Context context){
        super(context, DiceContract.getDbName(), null, DatabaseContract.version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DiceContract.getSqlCreateTable());

        //TODO WHY doesn't this work??
        /*
        ContentValues value = new ContentValues();
        db = getWritableDatabase();
        value.put(DiceContract.getNumberColName(), 0);
        db.insert(DiceContract.getTableName(), null, value);
        db.close();
        */
    }


    public void insertNumbers(){

        int diceNumbers[] = {4, 6, 8, 10, 12, 20};
        SQLiteDatabase db = getWritableDatabase();


        for (int number : diceNumbers){

            ContentValues value = new ContentValues();
            value.put(DiceContract.getNumberColName(), number);

            String findDiceNum = "SELECT " + DiceContract.getNumberColName() + " FROM " +
                    DiceContract.getTableName() + " WHERE " + DiceContract.getNumberColName() + "=" + number;

            try{
                Cursor c = db.rawQuery(findDiceNum, null);
                if (c.getCount() == 0) {
                    db.insert(DiceContract.getTableName(), null, value);
                }
            } catch (SQLiteException e){
                e.printStackTrace();
                Log.e(ERROR_SQLite, "Inserting a dice number failed");
            }
        }

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DiceContract.getTableName());
        onCreate(db);

    }

    public Boolean findDiceById(int diceId) {

       if (diceId >= 1 && diceId <= 6){
           return true;
       }
       else
           return false;

    }

    public Boolean findDiceByNumber(int number){

        switch(number){
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 20:
                return true;
            default:
                return false;
        }

    }
}
