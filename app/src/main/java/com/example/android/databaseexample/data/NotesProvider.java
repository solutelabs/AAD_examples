package com.example.android.databaseexample.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.databaseexample.data.NotesContract.UserEntry;
import com.example.android.databaseexample.data.NotesContract.NotesEntry;

/**
 * Notes Provider
 */

public class NotesProvider extends ContentProvider {
    public static final String LOG_TAG = NotesProvider.class.getSimpleName();
    /**
     * URI matcher code for the content URI for the User Table
     */
    private static final int USER = 100;
    /**
     * Uri matcher code for the content URI for the Notes Table
     */
    private static final int NOTES = 101;

    /**
     * URI matcher code for the content URI for the Notes Table based on Notes ID
     */
    private static final int NOTES_ID = 102;

    /**
     * UriMatcher object to match a content Uri to corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /**
         * The Content Uri of the form "content://com.example.android.databaseexample/user" will
         * map to the integer code {@link #USER}. The URI is used to provide access to Multiple
         * rows of the User table.
         */
        sUriMatcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.PATH_USER, USER);
        /**
         * The Content Uri of the form "content://com.example.android.databaseexample/notes" will
         * map to the integer code {@link #NOTES}. The URI is used to provide access to Multiple
         * rows of the User table.
         */
        sUriMatcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.PATH_NOTES, NOTES);
        /**
         * The Content Uri of the form "content://com.example.android.databaseexample/notes/#" will
         * map to the integer code {@link #NOTES_ID}. This Uri is used to provide access to one
         * single row of the notes table.
         * In this case, the "#" wildcard is used where "#" can be substitute for an integer.
         * For example, "content://com.example.android.databaseexample/notes/1" matches,but
         * "content://com.example.android.databaseexample/notes" (without a number at the end)
         * doesn't match.
         */
        sUriMatcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.PATH_NOTES + "/#", NOTES_ID);
    }

    private NotesHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new NotesHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                cursor = db.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case NOTES:
                cursor = db.query(NotesEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case NOTES_ID:
                if (selectionArgs != null && selectionArgs.length > 0) {
                    selection = NotesEntry._ID + "=? AND " + NotesEntry.COLUMN_USER_ID + "=?";
                    String[] newSelectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri)),
                            selectionArgs[0]};
                    cursor = db.query(NotesEntry.TABLE_NAME, projection, selection,
                            newSelectionArgs, null, null, sortOrder);
                } else {
                    throw new IllegalArgumentException("Selection Argument could not be null");
                }
                break;
            default:
                throw new IllegalArgumentException("Can not query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                return UserEntry.CONTENT_LIST_TYPE;
            case NOTES:
                return NotesEntry.CONTENT_LIST_TYPE;
            case NOTES_ID:
                return NotesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                return insertUser(uri, contentValues);
            case NOTES:
                return insertNotes(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertNotes(Uri uri, ContentValues contentValues) {
        String title = contentValues.getAsString(NotesEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Title required in Notes");
        }

        String description = contentValues.getAsString(NotesEntry.COLUMN_DESCRIPTION);
        if (description == null) {
            throw new IllegalArgumentException("Description required in Notes");
        }

        Integer userId = contentValues.getAsInteger(NotesEntry.COLUMN_USER_ID);
        if (userId == null) {
            throw new IllegalArgumentException("UserId required in Notes");
        }

        String creationTime = contentValues.getAsString(NotesEntry.COLUMN_CREATION_TIME);
        if (creationTime == null) {
            throw new IllegalArgumentException("creationtime required in Notes");
        }

        String updateTime = contentValues.getAsString(NotesEntry.COLUMN_UPDATE_TIME);
        if (updateTime == null) {
            throw new IllegalArgumentException("updateTime required in Notes");
        }


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(NotesEntry.TABLE_NAME, null, contentValues);
        if (id != -1) {
            return ContentUris.withAppendedId(uri, id);
        }
        Log.e(LOG_TAG, "Failed to insert row for " + uri);
        return null;
    }

    private Uri insertUser(Uri uri, ContentValues contentValues) {

        String firstname = contentValues.getAsString(UserEntry.COLUMN_FIRSTNAME);
        if (firstname == null) {
            throw new IllegalArgumentException("Firstname required in User");
        }

        String lastname = contentValues.getAsString(UserEntry.COLUMN_LASTNAME);
        if (lastname == null) {
            throw new IllegalArgumentException("lastname required in User");
        }

        String email = contentValues.getAsString(UserEntry.COLUMN_EMAIL);
        if (email == null) {
            throw new IllegalArgumentException("email required in User");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(UserEntry.TABLE_NAME, null, contentValues);
        if (id != -1) {
            return ContentUris.withAppendedId(uri, id);
        }
        Log.e(LOG_TAG, "Failed to insert row for " + uri);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArg) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowDeleted = 0;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                rowDeleted = db.delete(NotesEntry.TABLE_NAME, selection, selectionArg);
                break;
            case NOTES_ID:
                selection = NotesEntry._ID + "=?";
                selectionArg = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = db.delete(NotesEntry.TABLE_NAME, selection, selectionArg);
                break;
        }
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return updateNotes(uri, contentValues, selection, selectionArgs);
            case NOTES_ID:
                selection = NotesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateNotes(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private int updateNotes(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        if (contentValues.containsKey(NotesEntry.COLUMN_TITLE)) {
            String title = contentValues.getAsString(NotesEntry.COLUMN_TITLE);
            if (title == null) {
                throw new IllegalArgumentException("Title required in Notes");
            } else if (title != null && title.length() == 0) {
                throw new IllegalArgumentException("Title could not be null");
            }
        }
        if (contentValues.containsKey(NotesEntry.COLUMN_DESCRIPTION)) {
            String description = contentValues.getAsString(NotesEntry.COLUMN_DESCRIPTION);
            if (description == null) {
                throw new IllegalArgumentException("description required in Notes");
            } else if (description != null && description.length() == 0) {
                throw new IllegalArgumentException("description could not be null");
            }
        }
        if (contentValues.containsKey(NotesEntry.COLUMN_CREATION_TIME)) {
            String creationTime = contentValues.getAsString(NotesEntry.COLUMN_CREATION_TIME);
            if (creationTime == null) {
                throw new IllegalArgumentException("Creation Time required in Notes");
            }
        }
        if (contentValues.containsKey(NotesEntry.COLUMN_UPDATE_TIME)) {
            String updateTime = contentValues.getAsString(NotesEntry.COLUMN_UPDATE_TIME);
            if (updateTime == null) {
                throw new IllegalArgumentException("Update time required in Notes");
            }
        }
        if (contentValues.containsKey(NotesEntry.COLUMN_USER_ID)) {
            Integer userId = contentValues.getAsInteger(NotesEntry.COLUMN_USER_ID);
            if (userId == null) {
                throw new IllegalArgumentException("UserId must required");
            }
        }
        if (contentValues.size() == 0) {
            return 0;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowUpdated = db.update(NotesEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
