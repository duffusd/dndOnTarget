package com.example.dnd.data;

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
     * Returns the name of the database used for Character
     *
     * @return Name of the database used for Character
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }

    /**
     * Gets the SQL query used to create the table for Character
     *
     * @return SQL query used to create the table for Character
     * @author Atsuko Takanabe
     */
    public static String getSQLCreateTable() { return TABLE_CHARACTER_ATTACKS_CREATE; }

    /**
     * Gets the name of the database table used to store Character
     *
     * @return Name of the table in the database for Character
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }

    /**
     * Gets the name of CharacterID column in the database table
     *
     * @return Name of the CharacterID column
     * @author Atsuko Takanabe
     */
    public static String getCharacterIdColName() { return characterId; }

    /**
     * Gets the name of the AttackID column in the database table
     *
     * @return Name of the AttackID column
     * @author Atsuko Takanabe
     */
    public static String getAttackIdColName() { return attackId; }
    public static String getAttackName() { return attackName; }
}
