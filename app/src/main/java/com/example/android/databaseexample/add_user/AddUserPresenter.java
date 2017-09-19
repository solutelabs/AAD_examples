package com.example.android.databaseexample.add_user;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.android.databaseexample.data.NotesContract.UserEntry;
import com.example.android.databaseexample.data.NotesHelper;

/**
 * AddUser Presenter
 */

public class AddUserPresenter implements AddUserContract.Presenter {
    AddUserContract.View mView;
//    NotesHelper mNotesHelper;

    public AddUserPresenter(AddUserContract.View view) {
        mView = view;
//        mNotesHelper = new NotesHelper(mView.getContext());
    }

    @Override
    public void addUser(final String firstName, final String lastName, final String email) {
        if (!isFieldValid(firstName)) {
            if (mView != null) mView.showFirstNameError();
            return;
        }
        if (!isFieldValid(lastName)) {
            if (mView != null) mView.showLastNameError();
            return;
        }
        if (!isFieldValid(email)) {
            if (mView != null) mView.showEmailError();
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
//                SQLiteDatabase db = mNotesHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserEntry.COLUMN_FIRSTNAME, firstName);
                values.put(UserEntry.COLUMN_LASTNAME, lastName);
                values.put(UserEntry.COLUMN_EMAIL, email);
//                long newRawId = db.insert(UserEntry.TABLE_NAME, null, values);
                if (mView != null && mView.getContext() != null) {
                    Uri newUri = mView.getContext().getContentResolver().insert(UserEntry.CONTENT_URI, values);
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
                        mView.showSuccessForAddUser();
                    } else {
                        mView.showFailureForAddUser();
                    }
                }
            }
        };
        bg.execute();
    }

    @Override
    public void unSubscribe() {
//        mNotesHelper.close();
    }

    private boolean isFieldValid(String text) {
        if (!TextUtils.isEmpty(text) && text.length() > 0) {
            return true;
        }
        return false;
    }
}
