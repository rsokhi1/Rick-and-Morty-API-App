package com.example.rickandmortyapi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmortyapi.OnRowItemClickListener;
import com.example.rickandmortyapi.databinding.CharacterRowLayoutBinding;
import com.example.rickandmortyapi.databinding.TwolineRowLayoutBinding;
import com.example.rickandmortyapi.models.Episode;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ItemViewHolder>{
    private final Context context;
    private final ArrayList<Episode> itemArrayList;
    TwolineRowLayoutBinding binding;

    public EpisodeAdapter(Context context, ArrayList<Episode> items){
        this.itemArrayList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(TwolineRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Episode currentItem = itemArrayList.get(position);
        holder.bind(context, currentItem);
    }

    @Override
    public int getItemCount() {
        Log.d("CharacterAdapter", "getItemCount: Number of items " +this.itemArrayList.size() );
        return this.itemArrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TwolineRowLayoutBinding itemBinding;

        public ItemViewHolder(TwolineRowLayoutBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Episode currentItem){
            // TODO: Write the code to update recycler view's row layout
            itemBinding.tvLine1.setText(currentItem.getCode() +": " + currentItem.getTitle());
            itemBinding.tvLine2.setText("Originally Aired: " + currentItem.getAirDate());
        }
    }
}
