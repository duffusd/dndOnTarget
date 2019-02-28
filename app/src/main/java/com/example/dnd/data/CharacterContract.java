package com.example.dnd.data;

import android.provider.BaseColumns;

public final class CharacterContract {

        public static final class CharacterEntry implements BaseColumns{

            public static final String table_name = "charactersTable";
            public static final String _id = BaseColumns._ID;
            public static final String name_col = "name";
            public static final String characterAttacksId_col = "characterAttacksId";

        }
}



