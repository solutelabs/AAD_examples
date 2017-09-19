package com.example.android.databaseexample.home;

import android.content.Context;

import com.example.android.databaseexample.data.model.Notes;
import com.example.android.databaseexample.data.model.User;

import java.util.List;

/**
 * HomeContract
 */

public class HomeContract {
    interface View {
        void showAllUser(List<User> userList);

        void showNotes(List<Notes> notesList);

        Context getContext();

        void showLoadingIndicator(boolean isShow);
    }

    interface Presenter {
        void getAllUser();

        void getNotes(int userId);

        void unSubscribe();
    }
}
