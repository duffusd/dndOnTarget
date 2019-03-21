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

    /**
     * Returns the name of the database used for Dice
     *
     * @return Name of the database
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }

    /**
     * Returns the name of the database table used for Dice
     *
     * @return Name of the database table for Dice
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; };


    /**
     * Returns the name of DiceID column in the database table
     *
     * @return Name of DiceID column
     */
    public static String getIdColName() { return _id; };


    /**
     * Returns the name of DiceNumber column in the database table
     *
     * @return Name of DiceNumber column
     * @author Atsuko Takanabe
     */
    public static String getNumberColName() { return number; }


    /**
     * Returns the SQL query used for creating the database table for Dice
     *
     * @return SQL query for creating the database table
     * @author Atsuko Takanabe
     */
    public static String getSqlCreateTable() { return SQL_CREATE_TABLE_DICE; }


}
