package com.example.java_al_quran_app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_al_quran_app.R;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.databinding.ListItemSurahBinding;

import java.util.ArrayList;
import java.util.List;

public class ListSuratAdapter extends RecyclerView.Adapter<ListSuratAdapter.ListSuratViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<Surat> dataItems;

    public ListSuratAdapter(ArrayList<Surat> dataItems) {
        this.dataItems = dataItems;
    }

    public void updateData(ArrayList<Surat> newData) {
        dataItems.clear();
        dataItems = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListSuratAdapter.ListSuratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ListItemSurahBinding listItemSurahBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_surah, parent,false);
        return new ListSuratViewHolder(listItemSurahBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSuratAdapter.ListSuratViewHolder holder, int position) {
        holder.bind(dataItems.get(position));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ListSuratViewHolder extends RecyclerView.ViewHolder {
        ListItemSurahBinding listItemSurahBinding;

        public ListSuratViewHolder(ListItemSurahBinding listItemSurahBinding) {
            super(listItemSurahBinding.getRoot());
            this.listItemSurahBinding = listItemSurahBinding;
        }

        public void bind(Surat dataItem){
            listItemSurahBinding.tvNumber.setText(dataItem.getNomor());
            listItemSurahBinding.tvAyat.setText(dataItem.getNama());
            listItemSurahBinding.tvName.setText(dataItem.getUrut());
            listItemSurahBinding.tvInfo.setText(dataItem.getType() + " - " + dataItem.getAyat());
        }
    }
}
