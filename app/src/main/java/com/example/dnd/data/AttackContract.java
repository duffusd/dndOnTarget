package com.example.dnd.data;

import android.provider.BaseColumns;

public class AttackContract implements BaseColumns {

    private static final String db_name = "attacks.db";
    private static final String table_name = "attacksTable";
    private static final String _id = BaseColumns._ID;
    private static final String hitModifier = "hitModifier";
    private static final String damageModifier = "damageModifier";
    private static final String attack_dice_id = "attack_dice_id";

    private static final String SQL_CREATE_TABLE_ATTACKS=
            "CREATE TABLE " + table_name + " (" +
                    _id + " INTEGER PRIMARY KEY, " +
                    attack_dice_id + " INTEGER NOT NULL," +
                    hitModifier + " INTEGER," +
                    damageModifier + " INTEGER " +
                    ")";

    public static String getDbName() { return db_name; }
    public static String getSQLCreateTable() {
        return SQL_CREATE_TABLE_ATTACKS;
    }
    public static String getTableName() { return table_name; }
    public static String getIdColName() { return _id; }
    public static String getHitModifierColName() { return hitModifier; }
    public static String getDamageModifierColName() { return damageModifier; }
    public static String getAttackDiceIdColName() { return attack_dice_id; }
}
