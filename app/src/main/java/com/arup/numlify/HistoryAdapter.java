package com.arup.numlify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final List<History> histories;
    HistoryAdapter(List<History> histories) {
        this.histories = histories;
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
        holder.headingTextView.setText(histories.get(position).getValue());
        holder.textView.setText(histories.get(position).getAns());
        holder.time.setText(histories.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return histories.size();
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

