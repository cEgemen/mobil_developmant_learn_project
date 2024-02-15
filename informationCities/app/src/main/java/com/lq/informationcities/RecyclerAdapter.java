package com.lq.informationcities;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.informationcities.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycViewHandler> {

    ArrayList<CityModel> items;
    RecyclerAdapter(ArrayList<CityModel> items)
    {
         this.items = items;
         System.out.println("items.sizes() => "+items.size());
    }


    @NonNull
    @Override
    public RecycViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RecycViewHandler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycViewHandler holder, int position) {
        int index = position;
             System.out.println("items.get(position) => "+items.get(position));
             holder.binding.recyclerViewTextView.setText(items.get(position).getName());
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(holder.itemView.getContext(),DetayActivity.class);
                     SelectModel.init().setModel(items.get(index));
                     holder.itemView.getContext().startActivity(intent);
                 }
             });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class RecycViewHandler extends RecyclerView.ViewHolder{
         RecyclerItemBinding binding;
         public RecycViewHandler(RecyclerItemBinding binding) {
             super(binding.getRoot());
             this.binding = binding;
         }
     }


}
