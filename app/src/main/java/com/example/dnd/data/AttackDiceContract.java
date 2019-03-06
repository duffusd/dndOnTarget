package com.example.dnd.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class AttackDiceContract implements BaseColumns {

    private static final String db_name = "AttackDice.db";
    private static final String table_name = "attackDiceTable";
    private static final String _id = BaseColumns._ID;
    private static final String diceString_name = "diceString";
    private static final String SQL_CREATE_TABLE_ATTACKDICE=
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    diceString_name + " TEXT NOT NULL UNIQUE" +
                    ")";


    public static String getDbName() { return db_name; };
    public static String getTableName() { return table_name; }
    public static String getIdColName() { return _id; }
    public static String getDiceStringColName() { return diceString_name; }
    public static String getSqlCreateTable(){ return SQL_CREATE_TABLE_ATTACKDICE; }

}
