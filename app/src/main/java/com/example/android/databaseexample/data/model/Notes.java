package com.example.android.databaseexample.data.model;

import android.database.Cursor;

import com.example.android.databaseexample.Utils.DateUtils;
import com.example.android.databaseexample.data.NotesContract;

/**
 * Notes Model
 */

public class Notes {
    int id;
    String title;
    String description;
    String createTime;
    String updateTime;
    String userId;

    public static Notes fromCursor(Cursor cursor) {
        Notes notes = new Notes();
        notes.setId(cursor.getInt(
                cursor.getColumnIndex(NotesContract.NotesEntry._ID)));
        notes.setTitle(cursor.getString(
                cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE)));
        notes.setDescription(cursor.getString(
                cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION)));
        notes.setCreateTime(DateUtils.getFormatedDate(cursor.getString(
                cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CREATION_TIME))));
        notes.setUpdateTime(DateUtils.getFormatedDate(cursor.getString(
                cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_UPDATE_TIME))));
        notes.setUserId(cursor.getString(
                cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_USER_ID)));
        return notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
