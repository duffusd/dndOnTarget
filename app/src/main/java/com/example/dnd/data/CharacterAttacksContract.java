package com.example.dnd.data;

/**
 * CharacterAttacksContract class contains the constant definitions for the database table and the SQL queries
 * used for storing each attack for the character
 *
 */
public class CharacterAttacksContract {

    private static final String db_name = "CharacterAttacks.db";
    private static final String table_name = "characterAttacksTable";
    private static final String characterId = "characterId";
    private static final String attackId = "attackId";
    private static final String attackName = "attackName";

    // SQL to create a table
    private static final String TABLE_CHARACTER_ATTACKS_CREATE=
            "CREATE TABLE " + table_name + " (" +
                    characterId + " INTEGER, " +
                    attackId + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + characterId + ", " + attackId + "), " +
                    "FOREIGN KEY (" + characterId + ") " +
                    "REFERENCES " + CharacterContract.getTableName() + "( " + CharacterContract.getIdColName() + ") ON DELETE CASCADE," +
                    "FOREIGN KEY (" + attackId +
                    ") REFERENCES " + AttackContract.getTableName() + "( " + AttackContract.getIdColName() + ") ON DELETE CASCADE)";


    /**
     * Returns the name of the database that contains Character-Attack table
     *
     * @return Database name
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }


    /**
     * Returns the SQL query used for creating the Character-Attack table
     *
     * @return SQL query used for creating the table
     * @author Atsuko Takanabe
     */
    public static String getSQLCreateTable() { return TABLE_CHARACTER_ATTACKS_CREATE; }


    /**
     * Returns the name of the database table
     *
     * @return Name of the database table
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }


    /**
     * Returns the name of the CharacterID column in the table
     *
     * @return Name of the CharacterID column
     * @author Atsuko Takanabe
     */
    public static String getCharacterIdColName() { return characterId; }


    /**
     * Returns the name of the AttackID column in the table
     *
     * @return Name of the AttackID column
     * @author Atsuko Takanabe
     */
    public static String getAttackIdColName() { return attackId; }

}
