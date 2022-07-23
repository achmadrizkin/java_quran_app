package com.example.java_al_quran_app.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAyatAdapter extends RecyclerView.Adapter<ListAyatAdapter.ListAyatViewHolder> {
    @NonNull
    @Override
    public ListAyatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAyatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListAyatViewHolder extends RecyclerView.ViewHolder{
        public ListAyatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
