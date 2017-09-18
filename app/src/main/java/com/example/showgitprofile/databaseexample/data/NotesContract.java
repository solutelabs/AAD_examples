package com.example.showgitprofile.databaseexample.data;

import android.provider.BaseColumns;

/**
 * Notes Contract
 */

public final class NotesContract {
    private NotesContract() {
    }

    public static class UserEntry implements BaseColumns {
        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME = "user";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_FIRSTNAME = "firstname";
        public final static String COLUMN_LASTNAME = "lastname";
    }

    public static class NotesEntry implements BaseColumns {
        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME = "notes";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_CREATION_TIME = "create_time";
        public final static String COLUMN_UPDATE_TIME = "update_time";
        public final static String COLUMN_USER_ID = "user_id";
    }
}
