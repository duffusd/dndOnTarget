package com.example.dnd.data;

import android.provider.BaseColumns;

public final class CharacterContract implements BaseColumns {

    private static final String db_name = "Characters.db";
    private static final String table_name = "charactersTable";
    private static final String _id = BaseColumns._ID;
    private static final String name_col = "name";
    private static final String characterAttacksId_col = "characterAttacksId";

    private static final String SQL_CREATE_TABLE_CHARACTERS =
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    name_col + " TEXT NOT NULL UNIQUE, " +
                    characterAttacksId_col + " INTEGER" +
                    ")";

    /**
     * Returns the name of database used for Character class
     *
     * @return Name of the database for Character class
     * @author Atsuko Takanabe
     */
    public static String getDbName() { return db_name; }


    /**
     * Returns the SQL query used for creating the table for storing character information
     *
     * @return SQL query for creating the database table for storing characters
     * @author Atsuko Takanabe
     */
    public static String getSQLCreateTable() { return SQL_CREATE_TABLE_CHARACTERS; }


    /**
     * Returns the name of the table used for storing character information
     *
     * @return Name of the table used for storing characters
     * @author Atsuko Takanabe
     */
    public static String getTableName() { return table_name; }


    /**
     * Returns the name of CharacterID column in the database table
     *
     * @return Name of the CharacterID column
     * @author Atsuko Takanabe
     */
    public static String getIdColName() { return _id; }


    /**
     * Returns the name of the CharacterName column in the database table
     *
     * @return Name of the CharacterName column
     * @author Atsuko Takanabe
     */
    public static String getNameColName() { return name_col; }


    /**
     * Returns the name of AttackID column in the database table
     *
     * @return Name of the AttackID column
     * @author Atsuko Takanabe
     */
    public static String getCharacterAttacksIdColName() { return characterAttacksId_col; }

}



