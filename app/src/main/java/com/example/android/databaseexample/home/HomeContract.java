package com.example.android.databaseexample.home;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.example.android.databaseexample.data.model.Notes;
import com.example.android.databaseexample.data.model.User;

import java.util.List;

/**
 * HomeContract
 */

public class HomeContract {
    interface View {
        void showAllUser(List<User> userList);

        Context getContext();

        void showLoadingIndicator(boolean isShow);

        NotesCursorAdapter getAdapter();

        LoaderManager getLoader();
    }

    interface Presenter {

        void getNotes(int selectedUserId);

        void getAllUser();
    }
}
