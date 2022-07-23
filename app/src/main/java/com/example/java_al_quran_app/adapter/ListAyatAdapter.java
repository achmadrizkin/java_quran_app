package com.example.java_al_quran_app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_al_quran_app.R;
import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.databinding.ListItemAyatBinding;
import com.example.java_al_quran_app.databinding.ListItemSurahBinding;
import com.example.java_al_quran_app.view.AyatActivity;

import java.util.ArrayList;

public class ListAyatAdapter extends RecyclerView.Adapter<ListAyatAdapter.ListAyatViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<Ayat> dataItems;

    public ListAyatAdapter(ArrayList<Ayat> dataItems) {
        this.dataItems = dataItems;
    }

    public void updateData(ArrayList<Ayat> newData) {
        dataItems.clear();
        dataItems = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListAyatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ListItemAyatBinding listItemAyatBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_ayat, parent, false);
        return new ListAyatViewHolder(listItemAyatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAyatViewHolder holder, int position) {
        holder.bind(dataItems.get(position));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ListAyatViewHolder extends RecyclerView.ViewHolder{
        ListItemAyatBinding listItemAyatBinding;

        public ListAyatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ListAyatViewHolder(ListItemAyatBinding listItemAyatBinding) {
            super(listItemAyatBinding.getRoot());
            this.listItemAyatBinding = listItemAyatBinding;
        }

        public void bind(Ayat dataItem){
            listItemAyatBinding.tvArabic.setText(dataItem.getAr());
            listItemAyatBinding.tvTerjemahan.setText(dataItem.getId());
            listItemAyatBinding.tvNomorAyat.setText(dataItem.getNomor());
        }
    }
}
