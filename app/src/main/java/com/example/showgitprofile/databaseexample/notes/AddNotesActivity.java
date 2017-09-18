package com.example.showgitprofile.databaseexample.notes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.showgitprofile.databaseexample.R;
import com.example.showgitprofile.databaseexample.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Add Notes Fragment
 */

public class AddNotesActivity extends AppCompatActivity implements AddNotesContract.View {
    private EditText etTitle, etDescription;
    private FrameLayout flProgressbar;
    private Spinner spChooseUser;
    private ConstraintLayout clRootView;
    private int userId;
    private AddNotesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        bindView();
        init();
    }

    private void init() {
        presenter = new AddNotesPresenter(this);
        presenter.getAllUser();
        userId = -1;
    }

    private void bindView() {
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        spChooseUser = findViewById(R.id.sp_choose_user);
        clRootView = findViewById(R.id.cl_rootview);
        flProgressbar = findViewById(R.id.fl_progress_bg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_notes) {
            presenter.addNote(etTitle.getText().toString().trim(),
                    etDescription.getText().toString().trim(),
                    userId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showSuccessNotesMessage() {
        Toast.makeText(this, R.string.text_success_add_notes, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    private void showMessage(int text) {
        Snackbar.make(clRootView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFailureNotesMessage() {
        showMessage(R.string.text_failure_add_notes);
    }

    @Override
    public void showDescriptionError() {
        showMessage(R.string.text_description_error);
    }

    @Override
    public void showTitleError() {
        showMessage(R.string.text_title_error);
    }

    @Override
    public void showAllUser(final List<User> userList) {
        List<String> strUserList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            strUserList.add(userList.get(i).getEmail());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, strUserList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                tv.setText(userList.get(position).getEmail());
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spChooseUser.setAdapter(spinnerAdapter);
        spChooseUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = userList.get(i);
                if (i > 0) {
                    userId = selectedUser.getId();
                } else {
                    userId = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public void showUserIdError() {
        showMessage(R.string.text_choose_valid_user);
    }

    @Override
    public void showLoadingIndicator(boolean isShow) {
        flProgressbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
