package com.lq.artgalary;

import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.artgalary.databinding.ActivityArtActionBinding;
import com.lq.artgalary.databinding.RecycleItemLayoutBinding;

import java.util.ArrayList;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterViewHandler> {
     ArrayList<ArtModel> items;
     RecyclerViewAdapter(ArrayList<ArtModel> items){
          this.items = items;
     }

    @NonNull
    @Override
    public AdapterViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         RecycleItemLayoutBinding binding =RecycleItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterViewHandler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHandler holder, int position) {
         int index = position;
          holder.binding.artNameTextView.setText(items.get(position).getName());
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(holder.itemView.getContext(), artActionActivity.class);
                  intent.putExtra("type","old");
                  intent.putExtra("id",items.get(index).getId());
                  holder.itemView.getContext().startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
         System.out.println("=>=>=>===>=>"+items.size());
         return items.size();
    }

    public class AdapterViewHandler extends RecyclerView.ViewHolder{

         RecycleItemLayoutBinding binding;

        public AdapterViewHandler(@NonNull RecycleItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

