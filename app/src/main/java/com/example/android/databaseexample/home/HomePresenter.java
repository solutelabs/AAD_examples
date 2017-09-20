package com.example.android.databaseexample.home;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.android.databaseexample.Constants;
import com.example.android.databaseexample.data.NotesContract;
import com.example.android.databaseexample.data.NotesContract.UserEntry;
import com.example.android.databaseexample.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * HomePresenter
 */

public class HomePresenter implements HomeContract.Presenter {
    private static final int NOTES_LOADER = 0;
    private static final int USER_LOADER = 1;
    private HomeContract.View mView;

    LoaderManager.LoaderCallbacks<Cursor> mUserCursorLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] projection = {
                    UserEntry._ID,
                    UserEntry.COLUMN_EMAIL
            };
            return new CursorLoader(mView.getContext(),
                    UserEntry.CONTENT_URI, projection, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            getDataFromCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            getDataFromCursor(null);
            mView.showLoadingIndicator(false);
        }
    };

    LoaderManager.LoaderCallbacks<Cursor> mLoadCursorLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] projection = {
                    NotesContract.NotesEntry._ID,
                    NotesContract.NotesEntry.COLUMN_TITLE,
                    NotesContract.NotesEntry.COLUMN_DESCRIPTION,
                    NotesContract.NotesEntry.COLUMN_USER_ID,
                    NotesContract.NotesEntry.COLUMN_CREATION_TIME,
                    NotesContract.NotesEntry.COLUMN_UPDATE_TIME
            };
            String userId = bundle.getInt(Constants.USERID) + "";
            String selection = NotesContract.NotesEntry.COLUMN_USER_ID + "=?";
            String[] selectionArgs = {userId};

            return new CursorLoader(mView.getContext(), NotesContract.NotesEntry.CONTENT_URI,
                    projection, selection, selectionArgs, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            mView.getAdapter().changeCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mView.getAdapter().changeCursor(null);
        }
    };

    public HomePresenter(HomeContract.View view) {
        mView = view;
    }


    public void getDataFromCursor(final Cursor cursor) {
        if (cursor == null) {
            return;
        }
        AsyncTask<Void, Void, List<User>> bg = new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = new ArrayList<>();
                try {
                    while (cursor.moveToNext()) {
                        User user = new User();
                        user.setId(cursor.getInt(cursor.getColumnIndex(UserEntry._ID)));
                        user.setEmail(cursor
                                .getString(cursor.getColumnIndex(UserEntry.COLUMN_EMAIL)));
                        userList.add(user);
                    }
                    User user = new User();
                    if (userList.size() == 0) {
                        user.setEmail("Please add User");
                    } else {
                        user.setEmail("Select email id");
                    }
                    userList.add(0, user);
                } finally {
                    cursor.close();
                }

                return userList;
            }

            @Override
            protected void onPostExecute(List<User> userList) {
                super.onPostExecute(userList);
                if (mView != null) {
                    mView.showAllUser(userList);
                    mView.showLoadingIndicator(false);
                }
            }
        };
        bg.execute();
    }

    @Override
    public void getNotes(int selectedUserId) {
        mView.getAdapter().changeCursor(null);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.USERID, selectedUserId);
        mView.getLoader().restartLoader(NOTES_LOADER, bundle, mLoadCursorLoaderCallbacks);
    }

    @Override
    public void getAllUser() {
        mView.showLoadingIndicator(true);
        mView.getLoader().restartLoader(USER_LOADER, null, mUserCursorLoaderCallbacks);
    }

}
