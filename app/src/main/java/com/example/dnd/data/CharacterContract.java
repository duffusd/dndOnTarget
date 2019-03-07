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


    public static String getDbName() { return db_name; }
    public static String getSQLCreateTable() { return SQL_CREATE_TABLE_CHARACTERS; }
    public static String getTableName() { return table_name; }
    public static String getIdColName() { return _id; }
    public static String getNameColName() { return name_col; }
    public static String getCharacterAttacksIdColName() { return characterAttacksId_col; }

}



