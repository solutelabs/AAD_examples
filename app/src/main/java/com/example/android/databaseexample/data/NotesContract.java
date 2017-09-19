package com.example.android.databaseexample.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Notes Contract
 */

public final class NotesContract {
    private NotesContract() {
    }

    public final static String CONTENT_AUTHORITY = "com.example.android.databaseexample";
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public final static String PATH_USER = "user";
    public final static String PATH_NOTES = "notes";

    public static class UserEntry implements BaseColumns {
        /**
         * The content URI to access the pet data in the provider
         */
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USER);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of Notes.
         */
        public final static String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single Notes.
         */
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME = "user";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_FIRSTNAME = "firstname";
        public final static String COLUMN_LASTNAME = "lastname";
    }

    public static class NotesEntry implements BaseColumns {
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of Notes.
         */
        public final static String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single Notes.
         */
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME = "notes";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_CREATION_TIME = "create_time";
        public final static String COLUMN_UPDATE_TIME = "update_time";
        public final static String COLUMN_USER_ID = "user_id";
    }
}
