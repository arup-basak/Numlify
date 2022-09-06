package com.arup.numlify;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final Cursor cursor;

    HistoryAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        cursor.moveToNext();
        holder.headingTextView.setText(cursor.getString(0));
        holder.textView.setText(cursor.getString(1));
        holder.time.setText(cursor.getString(2));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        final TextView headingTextView;
        final TextView textView;
        final TextView time;
        public HistoryViewHolder(View view) {
            super(view);
            headingTextView = view.findViewById(R.id.numb);
            textView = view.findViewById(R.id.word);
            time = view.findViewById(R.id.time);
        }
    }

}

