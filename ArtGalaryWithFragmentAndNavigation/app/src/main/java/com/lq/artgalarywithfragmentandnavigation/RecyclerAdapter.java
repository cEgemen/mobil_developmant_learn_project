package com.lq.artgalarywithfragmentandnavigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.artgalarywithfragmentandnavigation.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<AdapterViewHolder> {
      ArrayList<ArtModel> items;
      RecyclerAdapter(ArrayList<ArtModel> items)
      {
           this.items = items;
      }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
                     int index = position;
                     holder.binding.artNameTextView.setText((position + 1)+". Art  = "+items.get(index));
                     holder.binding.artNameTextView.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             RecyclerFragmentDirections.ActionRecyclerFragmentToArtAddFragment action;
                             action = RecyclerFragmentDirections.actionRecyclerFragmentToArtAddFragment(new ArtModel("old",items.get(index).name,items.get(index).id));

                                 Navigation.findNavController(holder.itemView).navigate(action);

                         }
                     });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

 class AdapterViewHolder extends RecyclerView.ViewHolder{
       RecyclerItemBinding binding;
     public AdapterViewHolder(@NonNull RecyclerItemBinding binding) {
         super(binding.getRoot());
         this.binding = binding;
     }
 }
