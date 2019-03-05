package com.example.dnd.data;

public class CharacterAttacksContract {

    private static final String db_name = "characterAttacks.db";
    private static final String table_name = "characterAttacksTable";
    private static final String characterId = "characterId";
    private static final String attackId = "attackId";

    // SQL to create a table
    private static final String TABLE_CHARACTER_ATTACKS_CREATE=
            "CREATE TABLE " + CharacterAttacksContract.table_name + " (" +
                    CharacterAttacksContract.characterId + " INTEGER NOT NULL, " +
                    CharacterAttacksContract.attackId + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + CharacterAttacksContract.characterId + ", " +
                    CharacterAttacksContract.attackId + "), " +
                    "FOREIGN KEY (" + CharacterAttacksContract.characterId + ") " +
                    "REFERENCES " + CharacterContract.getTableName() + "( " + CharacterContract.getIdColName() + ") ON DELETE CASCADE," +
                    "FOREIGN KEY (" + CharacterAttacksContract.attackId +
                    ") REFERENCES " + AttackContract.getTableName() + "( " + AttackContract.getIdColName() + ") ON DELETE CASCADE)";



    public static String getDbName() { return db_name; }
    public static String getSQLCreateTable() { return TABLE_CHARACTER_ATTACKS_CREATE; }
    public static String getTableName() { return table_name; }
    public static String getCharacterIdColName() { return characterId; }
    public static String getAttackIdColName() { return attackId; }
}
