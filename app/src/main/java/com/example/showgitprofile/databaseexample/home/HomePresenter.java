package com.example.showgitprofile.databaseexample.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.showgitprofile.databaseexample.Utils.DateUtils;
import com.example.showgitprofile.databaseexample.data.NotesContract.NotesEntry;
import com.example.showgitprofile.databaseexample.data.NotesContract.UserEntry;
import com.example.showgitprofile.databaseexample.data.NotesHelper;
import com.example.showgitprofile.databaseexample.data.model.Notes;
import com.example.showgitprofile.databaseexample.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * HomePresenter
 */

public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View mView;
    NotesHelper mNotesHelper;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mNotesHelper = new NotesHelper(mView.getContext());
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
                SQLiteDatabase db = mNotesHelper.getReadableDatabase();
                String[] projection = {
                        UserEntry._ID,
                        UserEntry.COLUMN_EMAIL
                };
                Cursor cursor = db.query(UserEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
                List<User> userList = new ArrayList<>();
                try {
                    while (cursor.moveToNext()) {
                        User user = new User();
                        user.setId(cursor.getInt(cursor.getColumnIndex(UserEntry._ID)));
                        user.setEmail(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_EMAIL)));
                        userList.add(user);
                    }
                } finally {
                    cursor.close();
                }
                return userList;
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

    @Override
    public void getNotes(final int userId) {
        AsyncTask<Void, Void, List<Notes>> bg = new AsyncTask<Void, Void, List<Notes>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLoadingIndicator(true);
            }

            @Override
            protected List<Notes> doInBackground(Void... voids) {
                SQLiteDatabase db = mNotesHelper.getReadableDatabase();
                String[] projection = {
                        NotesEntry._ID,
                        NotesEntry.COLUMN_TITLE,
                        NotesEntry.COLUMN_DESCRIPTION,
                        NotesEntry.COLUMN_USER_ID,
                        NotesEntry.COLUMN_CREATION_TIME,
                        NotesEntry.COLUMN_UPDATE_TIME
                };
                String selection = NotesEntry.COLUMN_USER_ID + "=?";
                String[] selectionArgs = {userId + ""};
                Cursor cursor = db.query(NotesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                List<Notes> notesList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Notes notes = new Notes();
                    notes.setId(cursor.getInt(
                            cursor.getColumnIndex(NotesEntry._ID)));
                    notes.setTitle(cursor.getString(
                            cursor.getColumnIndex(NotesEntry.COLUMN_TITLE)));
                    notes.setDescription(cursor.getString(
                            cursor.getColumnIndex(NotesEntry.COLUMN_DESCRIPTION)));
                    notes.setCreateTime(DateUtils.getFormatedDate(cursor.getString(
                            cursor.getColumnIndex(NotesEntry.COLUMN_CREATION_TIME))));
                    notes.setUpdateTime(DateUtils.getFormatedDate(cursor.getString(
                            cursor.getColumnIndex(NotesEntry.COLUMN_UPDATE_TIME))));
                    notes.setUserId(cursor.getString(
                            cursor.getColumnIndex(NotesEntry.COLUMN_USER_ID)));
                    notesList.add(notes);
                }
                return notesList;
            }

            @Override
            protected void onPostExecute(List<Notes> notesList) {
                super.onPostExecute(notesList);
                if (mView != null) {
                    mView.showLoadingIndicator(false);
                    mView.showNotes(notesList);
                }
            }
        };
        bg.execute();
    }

    @Override
    public void unSubscribe() {
        if (mNotesHelper != null) {
            mNotesHelper.close();
        }
    }
}
