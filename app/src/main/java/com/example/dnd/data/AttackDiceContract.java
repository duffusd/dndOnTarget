package com.example.dnd.data;

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


    /**
     * Gets the name of the database for Dice
     *
     * @return The database name
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; };

    /**
     * Gets the name of the database table for Dice
     *
     * @return Name of the database table used for Dice
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }

    /**
     * Gets the name of the AttackID column in the table
     * @return Name of the AttackID column
     * @author Atsuko Takanabe
     * */
    public static String getAttackIdColName() { return attackId; }

    /**
     * Gets the name of DiceID column
     *
     * @return Name of the DiceID column
     * @author Atsuko Takanabe
     */
    public static String getDiceIdColName() { return diceId; }

    /**
     * Returns the SQL query used to create the database table for AttackDice
     *
     * @return SQL query for creating the table
     * @author Atsuko Takanabe
     */
    public static String getSqlCreateTable(){ return SQL_CREATE_TABLE_ATTACKDICE; }

}
