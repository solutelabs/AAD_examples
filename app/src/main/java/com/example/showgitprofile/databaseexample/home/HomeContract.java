package com.example.showgitprofile.databaseexample.home;

import android.content.Context;

import com.example.showgitprofile.databaseexample.data.model.Notes;
import com.example.showgitprofile.databaseexample.data.model.User;

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
