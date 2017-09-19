package com.example.android.databaseexample.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.databaseexample.R;
import com.example.android.databaseexample.data.model.Notes;

import java.util.List;

/**
 * Notes Adapter to show Data
 */

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Notes> mNotesList;

    void updateNotesData(List<Notes> notesList) {
        mNotesList = notesList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Notes notes = mNotesList.get(position);
        NotesViewHolder viewHolder = (NotesViewHolder) holder;
        if (notes != null) {
            if (notes.getTitle() != null) {
                viewHolder.mTvTitle.setText(notes.getTitle());
            } else {
                viewHolder.mTvTitle.setText("");
            }
            if (notes.getDescription() != null) {
                viewHolder.mTvDescription.setText(notes.getDescription());
            } else {
                viewHolder.mTvDescription.setText("");
            }
            if (notes.getUpdateTime() != null) {
                viewHolder.mTvUpdateTime.setText(notes.getUpdateTime());
            } else {
                viewHolder.mTvUpdateTime.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNotesList != null ? mNotesList.size() : 0;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvTitle, mTvDescription, mTvUpdateTime;

        public NotesViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mTvUpdateTime = itemView.findViewById(R.id.tv_updated_time);
        }
    }
}
