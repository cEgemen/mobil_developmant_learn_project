package com.lq.coins;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.coins.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterViewHolder> {
     String [] colors = {"#edd000","#054170","#664499","#66cdaa","#ea9782","#559933","#e1d4c0","#e97042"} ;
     ArrayList<CoinModel> items ;
     RecyclerAdapter(ArrayList<CoinModel> items)
     {
         this.items = items;
     }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecyclerItemBinding binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RecyclerAdapter.AdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
              holder.binding.coinNameTextView.setText(items.get(position).coinName);
              holder.binding.coinPriceNameTextView.setText(items.get(position).coinPair);
              holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 7 ]));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static  class AdapterViewHolder extends RecyclerView.ViewHolder{
          RecyclerItemBinding binding;
         public AdapterViewHolder(RecyclerItemBinding binding) {
             super(binding.getRoot());
             this.binding = binding;
         }
     }
}
