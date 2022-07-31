package com.example.rickandmortyapi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmortyapi.OnRowItemClickListener;
import com.example.rickandmortyapi.databinding.CharacterRowLayoutBinding;
import com.example.rickandmortyapi.models.RickAndMortyCharacter;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>{

    private final Context context;
    private final ArrayList<RickAndMortyCharacter> itemArrayList;
    CharacterRowLayoutBinding binding;


    private final OnRowItemClickListener clickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public CharacterAdapter(Context context, ArrayList<RickAndMortyCharacter> items,OnRowItemClickListener clickListener){
        this.itemArrayList = items;
        this.context = context;
        this.clickListener = clickListener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CharacterRowLayoutBinding itemBinding;
        public ItemViewHolder(CharacterRowLayoutBinding binding) {
            super(binding.getRoot());
            // Define click listener for the ViewHolder's View
            this.itemBinding = binding;
        }

        public void bind(Context context, RickAndMortyCharacter currentItem, OnRowItemClickListener clickListener){
            // TODO: Update the UI for the row
            itemBinding.tvCharacterName.setText(currentItem.getName());
            itemBinding.tvOrigin.setText("Originally from: " + currentItem.getOrigin().getName());
            itemBinding.tvLastKnownLocation.setText("Last Known Location: " + currentItem.getLastKnownLocation().getName());

            Glide.with(context).load(currentItem.getImageURL()).into(itemBinding.ivCharacterPhoto);

            itemBinding.tvStatus.setText(currentItem.getStatus());

            String currStatusStr = currentItem.getStatus();

            if(currStatusStr.contentEquals("Dead")){
                itemBinding.tvStatus.setBackgroundColor(Color.parseColor("#c0392b"));
            }
            else if(currStatusStr.contentEquals("Alive")){
                itemBinding.tvStatus.setBackgroundColor(Color.parseColor("#27ae60"));
            }
            else{
                itemBinding.tvStatus.setBackgroundColor(Color.parseColor("#34495e"));
            }



            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnItemClickListener(currentItem);
                }
            });
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        return new ItemViewHolder(CharacterRowLayoutBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, final int position) {

        RickAndMortyCharacter currentItem = itemArrayList.get(position);
        viewHolder.bind(context,currentItem,clickListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("CharacterAdapter","GetItemCount: Number of items " + this.itemArrayList.size());
        return this.itemArrayList.size();
    }
}
