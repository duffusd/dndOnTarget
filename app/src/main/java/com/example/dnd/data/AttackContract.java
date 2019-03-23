package com.example.dnd.data;

import android.provider.BaseColumns;

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
     * Gets the name of the database for Attack
     *
     * @return the database name
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }

    /**
     * Gets the sql query that creates the table for attacks
     *
     * @return The SQL query
     * @author Atsuko Takanabe
     */
    public static String getSQLCreateTable() {
        return SQL_CREATE_TABLE_ATTACKS;
    }

    /**
     * Gets the name of the table for attacks in the database
     *
     * @return Table name for attacks
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }

    /**
     * Gets the name of attack Id column in the table
     *
     * @return The name of attack ID column
     * @author Atsuko Takanabe
     */
    public static String getIdColName() { return _id; }

    /**
     * Gets the name of hit modifier column in the table
     *
     * @return The name of hit modifier column
     * @author Atsuko Takanabe
     */
    public static String getHitModifierColName() { return hitModifier; }

    /**
     * Gets the name of damage modifiler column in the table
     *
     * @return The name of damage modifier column
     * @author Atsuko Takanabe
     */
    public static String getDamageModifierColName() { return damageModifier; }

    /**
     * Gets the name of Dice ID column in the table
     *
     * @return The name of Dice ID column
     * @author Atsuko Takanabe
     */
    public static String getDiceIdColName() { return dice_id; }

    /**
     * Gets the name of attack name column in the table
     *
     * @return The name of attack name column
     * @author Atsuko Takanabe
     */
    public static String getAttackNameColName() { return attackName; }

    public static String getNumOfDieColName() { return numOfDie; }
}
