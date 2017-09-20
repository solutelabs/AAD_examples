package com.example.android.databaseexample.home;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.databaseexample.Constants;
import com.example.android.databaseexample.R;
import com.example.android.databaseexample.add_user.AddUserActivity;
import com.example.android.databaseexample.data.model.User;
import com.example.android.databaseexample.notes.AddNotesActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private RecyclerView rvShowNotes;
    private Button btnAddNotes, btnAddUser;
    private Spinner spChooseUser;
    private FrameLayout flProgressbar;
    private HomePresenter presenter;
    private NotesCursorAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        init();
        setListener();
    }

    private void bindView() {
        rvShowNotes = findViewById(R.id.rv_show_notes);
        btnAddUser = findViewById(R.id.btn_add_user);
        btnAddNotes = findViewById(R.id.btn_add_notes);
        spChooseUser = findViewById(R.id.sp_choose_user);
        flProgressbar = findViewById(R.id.fl_progress_bg);
    }

    private void init() {
        presenter = new HomePresenter(this);
        notesAdapter = new NotesCursorAdapter(null);
        rvShowNotes.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvShowNotes.setAdapter(notesAdapter);
        presenter.getAllUser();
    }

    private void setListener() {
        btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddNotesActivity.class);
                startActivity(intent);
            }
        });
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddUserActivity.class);
                startActivityForResult(intent, Constants.ADD_USER_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ADD_USER_CODE) {
            if (resultCode == RESULT_OK) {
                notesAdapter.changeCursor(null);
                presenter.getAllUser();
            }
        }
    }

    @Override
    public void showAllUser(final List<User> userList) {
        List<String> strUserList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            strUserList.add(userList.get(i).getEmail());
        }
        final ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(this, R.layout.spinner_item, strUserList) {
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spChooseUser.setAdapter(spinnerArrayAdapter);
        spChooseUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                if (position > 0) {
                    presenter.getNotes(selectedUser.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoadingIndicator(boolean isShow) {
        flProgressbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public NotesCursorAdapter getAdapter() {
        return notesAdapter;
    }

    @Override
    public LoaderManager getLoader() {
        return getLoaderManager();
    }
}
