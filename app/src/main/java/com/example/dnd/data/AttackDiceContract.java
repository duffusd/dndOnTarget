package com.example.dnd.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class AttackDiceContract implements BaseColumns {

    private static final String db_name = "AttackDice.db";
    private static final String table_name = "attackDiceTable";
    private static final String attackId = "attackId";
    private static final String diceId = "diceId";
    private static final String SQL_CREATE_TABLE_ATTACKDICE=
            "CREATE TABLE " + table_name + " (" +
                    attackId + " INTEGER NOT NULL, " +
                    diceId + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + attackId + ") " +
                    "REFERENCES " + AttackContract.getTableName() + " (" + AttackContract.getIdColName() + "), " +
                    "FOREIGN KEY (" + diceId + ") " +
                    "REFERENCES " + DiceContract.getTableName() + " (" + DiceContract.getIdColName() + ")" +
                    ")";


    public static String getDbName() { return db_name; };
    public static String getTableName() { return table_name; }
    public static String getAttackIdColName() { return attackId; }
    public static String getDiceIdColName() { return diceId; }
    public static String getSqlCreateTable(){ return SQL_CREATE_TABLE_ATTACKDICE; }

}
