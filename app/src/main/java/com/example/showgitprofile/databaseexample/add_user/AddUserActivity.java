package com.example.showgitprofile.databaseexample.add_user;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.showgitprofile.databaseexample.Constants;
import com.example.showgitprofile.databaseexample.R;

/**
 * AddUser fragment
 */

public class AddUserActivity extends AppCompatActivity implements AddUserContract.View {
    private EditText etFirstName, etLastName, etEmail;
    private ConstraintLayout clRootView;
    private FrameLayout flProgressbar;
    private AddUserPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        bindView();
        init();
    }

    private void init() {
        presenter = new AddUserPresenter(this);
    }

    private void bindView() {
        etFirstName = findViewById(R.id.et_firstname);
        etLastName = findViewById(R.id.et_lastname);
        etEmail = findViewById(R.id.et_email);
        clRootView = findViewById(R.id.cl_rootview);
        flProgressbar = findViewById(R.id.fl_progress_bg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_user) {
            presenter.addUser(etFirstName.getText().toString().trim(),
                    etLastName.getText().toString().trim(),
                    etEmail.getText().toString().trim());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showSuccessForAddUser() {
        Toast.makeText(this, R.string.text_success_add_user, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    private void showMessage(int text) {
        Snackbar.make(clRootView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFailureForAddUser() {
        showMessage(R.string.text_failure_add_user);
    }

    @Override
    public void showFirstNameError() {
        showMessage(R.string.text_first_name_error);
    }

    @Override
    public void showLastNameError() {
        showMessage(R.string.text_last_name_error);
    }

    @Override
    public void showEmailError() {
        showMessage(R.string.text_email_error);
    }

    @Override
    public void showLoadingIndicator(boolean isShow) {
        flProgressbar.setVisibility(isShow? View.VISIBLE:View.GONE);
    }
}
