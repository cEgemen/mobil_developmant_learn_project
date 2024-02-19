package com.lq.myfavoritelocation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.myfavoritelocation.RoomDatabase.FavoriLocation;
import com.lq.myfavoritelocation.databinding.RecycleItemBinding;

import java.util.ArrayList;

 class RecycleViewAdapter extends RecyclerView.Adapter<AdapterViewHandler> {
    ArrayList<FavoriLocation> items;
    RecycleViewAdapter(ArrayList<FavoriLocation> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public AdapterViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleItemBinding binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterViewHandler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHandler holder, int position) {
        int index = position;
        holder.binding.locationTextView.setText(items.get(index).locationName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                intent.putExtra("type","oldLocation");
                intent.putExtra("selectedLocation",items.get(index));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class AdapterViewHandler extends RecyclerView.ViewHolder{

    RecycleItemBinding binding ;

    public AdapterViewHandler(@NonNull RecycleItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
