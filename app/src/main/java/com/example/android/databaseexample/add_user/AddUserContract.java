package com.example.android.databaseexample.add_user;

import android.content.Context;

/**
 * AddUserContract
 */

public class AddUserContract {
    interface View {
        Context getContext();

        void showSuccessForAddUser();

        void showFailureForAddUser();

        void showFirstNameError();

        void showLastNameError();

        void showEmailError();

        void showLoadingIndicator(boolean isShow);
    }

    interface Presenter {
        void addUser(String firstName, String lastName, String email);

        void unSubscribe();
    }
}
