package com.example.android.databaseexample.notes;

import android.content.Context;

import com.example.android.databaseexample.data.model.User;

import java.util.List;

/**
 * AddNotes Contract
 */

public class AddNotesContract {
    interface View {
        Context getContext();

        void showSuccessNotesMessage();

        void showFailureNotesMessage();

        void showDescriptionError();

        void showTitleError();

        void showAllUser(List<User> userList);

        void showUserIdError();

        void showLoadingIndicator(boolean isShow);
    }

    interface Presenter {
        void addNote(String title, String description, int userId);

        void getAllUser();

        void unSubscribe();

    }
}
