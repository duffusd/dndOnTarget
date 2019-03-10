package com.example.dnd.data;

import android.provider.BaseColumns;

public class DiceContract implements BaseColumns {

    private static final String db_name = "Dice.db";
    private static final String table_name = "diceTable";
    private static final String _id = BaseColumns._ID;
    private static final String number = "number";

    /*
    private static final String SQL_CREATE_TABLE_DICE =
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    number + " INTEGER)";
*/

    private static final String SQL_CREATE_TABLE_DICE =
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    number + " INTEGER NOT NULL UNIQUE" +
                    ")";

    public static String getDbName() { return db_name; }
    public static String getTableName() { return table_name; };
    public static String getIdColName() { return _id; };
    public static String getNumberColName() { return number; }
    public static String getSqlCreateTable() { return SQL_CREATE_TABLE_DICE; }


}
