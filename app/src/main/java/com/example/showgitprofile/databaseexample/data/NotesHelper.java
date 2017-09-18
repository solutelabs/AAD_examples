package com.example.showgitprofile.databaseexample.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.showgitprofile.databaseexample.data.NotesContract.UserEntry;
import com.example.showgitprofile.databaseexample.data.NotesContract.NotesEntry;

/**
 * Database Helper for Notes App. Manages Database creation and version management.
 */

public class NotesHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;

    public NotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Create user table query
         */
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserEntry.COLUMN_FIRSTNAME + " TEXT NOT NULL,"
                + UserEntry.COLUMN_LASTNAME + " TEXT NOT NULL,"
                + UserEntry.COLUMN_EMAIL + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_USER_TABLE);

        /**
         * create note table
         */
        String SQL_CREATE_NOTE_TABLE = "CREATE TABLE " + NotesEntry.TABLE_NAME + " ("
                + NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NotesEntry.COLUMN_TITLE + " TEXT NOT NULL,"
                + NotesEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + NotesEntry.COLUMN_USER_ID + " INTEGER NOT NULL,"
                + NotesEntry.COLUMN_CREATION_TIME + " TEXT NOT NULL DEFAULT 0,"
                + NotesEntry.COLUMN_UPDATE_TIME + " TEXT NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(" + NotesEntry.COLUMN_USER_ID + ") REFERENCES " + UserEntry.TABLE_NAME
                + "(" + UserEntry._ID + "));";

        db.execSQL(SQL_CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /**
         * The database is still at version 1, so there is nothing to do be done here.
         */
    }
}
