package com.example.android.databaseexample.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.android.databaseexample.data.NotesContract;
import com.example.android.databaseexample.data.NotesContract.NotesEntry;
import com.example.android.databaseexample.data.NotesContract.UserEntry;
import com.example.android.databaseexample.data.NotesHelper;
import com.example.android.databaseexample.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * AddNotesPresenter
 */

public class AddNotesPresenter implements AddNotesContract.Presenter {
    AddNotesContract.View mView;
//    NotesHelper mNotesHelper;

    public AddNotesPresenter(AddNotesContract.View view) {
        mView = view;
//        mNotesHelper = new NotesHelper(mView.getContext());
    }

    @Override
    public void addNote(final String title, final String description, final int userId) {
        if (!isFieldValid(title)) {
            if (mView != null) {
                mView.showTitleError();
            }
            return;
        }
        if (!isFieldValid(description)) {

            if (mView != null) {
                mView.showDescriptionError();
            }
            return;
        }
        if (userId == -1) {
            mView.showUserIdError();
            return;
        }
        AsyncTask<Void, Void, Uri> bg = new AsyncTask<Void, Void, Uri>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLoadingIndicator(true);
            }

            @Override
            protected Uri doInBackground(Void... voids) {
                Long currentTime = System.currentTimeMillis();
//                SQLiteDatabase db = mNotesHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(NotesEntry.COLUMN_TITLE, title);
                values.put(NotesEntry.COLUMN_DESCRIPTION, description);
                values.put(NotesEntry.COLUMN_USER_ID, userId);
                values.put(NotesEntry.COLUMN_CREATION_TIME, currentTime);
                values.put(NotesEntry.COLUMN_UPDATE_TIME, currentTime);
//                long newRawId = db.insert(NotesEntry.TABLE_NAME, null, values);
                if (mView != null && mView.getContext() != null) {
                    Uri newUri = mView.getContext().getContentResolver()
                            .insert(NotesEntry.CONTENT_URI, values);
                    return newUri;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Uri newUri) {
                super.onPostExecute(newUri);
                if (mView != null) {
                    mView.showLoadingIndicator(false);
                    if (newUri != null) {
                        mView.showSuccessNotesMessage();
                    } else {
                        mView.showFailureNotesMessage();
                    }
                }
            }
        };
        bg.execute();


    }

    @Override
    public void getAllUser() {
        AsyncTask<Void, Void, List<User>> bg = new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLoadingIndicator(true);
            }

            @Override
            protected List<User> doInBackground(Void... voids) {
//                SQLiteDatabase db = mNotesHelper.getReadableDatabase();

                String[] projection = {
                        NotesContract.UserEntry._ID,
                        NotesContract.UserEntry.COLUMN_EMAIL
                };

                if (mView != null && mView.getContext() != null) {

//                Cursor cursor = db.query(NotesContract.UserEntry.TABLE_NAME,
//                        projection,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null);

                    Cursor cursor = mView.getContext().getContentResolver()
                            .query(UserEntry.CONTENT_URI, projection,
                                    null, null, null);

                    List<User> userList = new ArrayList<>();

                    try {
                        while (cursor.moveToNext()) {
                            User user = new User();
                            user.setId(cursor
                                    .getInt(cursor.getColumnIndex(NotesContract.UserEntry._ID)));
                            user.setEmail(cursor
                                    .getString(cursor
                                            .getColumnIndex(NotesContract.UserEntry.COLUMN_EMAIL)));
                            userList.add(user);
                        }
                    } finally {
                        cursor.close();
                    }

                    return userList;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<User> userList) {
                super.onPostExecute(userList);
                if (mView != null) {
                    mView.showLoadingIndicator(false);
                    User user = new User();
                    if (userList.size() == 0) {
                        user.setEmail("Please add User");
                    } else {
                        user.setEmail("Select email id");
                    }
                    userList.add(0, user);

                    mView.showAllUser(userList);
                }
            }
        };
        bg.execute();


    }

    private boolean isFieldValid(String text) {
        if (!TextUtils.isEmpty(text) && text.length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void unSubscribe() {
//        if (mNotesHelper != null) {
//            mNotesHelper.close();
//        }
    }
}
