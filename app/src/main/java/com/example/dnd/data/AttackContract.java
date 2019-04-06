package com.example.dnd.data;

import android.provider.BaseColumns;


/**
 * AttackContract class contains the constant definitions for the database table and the SQL queries
 * used for Attack class
 *
 */
public class AttackContract implements BaseColumns {

    private static final String db_name = "Attacks.db";
    private static final String table_name = "attacksTable";
    private static final String _id = BaseColumns._ID;
    private static final String attackName = "attackName";
    private static final String hitModifier = "hitModifier";
    private static final String damageModifier = "damageModifier";
    private static final String dice_id = "dice_id";
    private static final String numOfDie = "numOfDie";

    private static final String SQL_CREATE_TABLE_ATTACKS=
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    attackName + " TEXT NOT NULL, " +
                    dice_id + " INTEGER," +
                    hitModifier + " INTEGER," +
                    damageModifier + " INTEGER, " +
                    numOfDie + " INTEGER " +
                    ")";

    /**
     * Returns the name of the database for Attack class
     *
     * @return The database name
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }


    /**
     * Returns the sql query for creating database table for the Attack class
     *
     * @return The SQL query
     * @author Atsuko Takanabe
     */
    public static String getSQLCreateTable() {
        return SQL_CREATE_TABLE_ATTACKS;
    }


    /**
     * Returns the name of the table for Attack class
     *
     * @return Table name for attacks
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }


    /**
     * Returns the name of the attack Id column in the table
     *
     * @return The name of attack ID column
     * @author Atsuko Takanabe
     */
    public static String getIdColName() { return _id; }


    /**
     * Returns the name of the hit modifier column in the table
     *
     * @return The name of hit modifier column
     * @author Atsuko Takanabe
     */
    public static String getHitModifierColName() { return hitModifier; }


    /**
     * Returns the name of the damage modifier column in the table
     *
     * @return The name of damage modifier column
     * @author Atsuko Takanabe
     */
    public static String getDamageModifierColName() { return damageModifier; }


    /**
     * Returns the name of the Dice ID column in the table
     *
     * @return The name of Dice ID column
     * @author Atsuko Takanabe
     */
    public static String getDiceIdColName() { return dice_id; }


    /**
     * Returns the name of the attack name column in the database table
     *
     * @return The name of attack name column
     * @author Atsuko Takanabe
     */
    public static String getAttackNameColName() { return attackName; }



    /**
     * Returns the name of the number of dice column in the database table
     *
     * @return The name of the column for Number of Die
     * @author Atsuko Takanabe
     */
    public static String getNumOfDieColName() { return numOfDie; }
}
