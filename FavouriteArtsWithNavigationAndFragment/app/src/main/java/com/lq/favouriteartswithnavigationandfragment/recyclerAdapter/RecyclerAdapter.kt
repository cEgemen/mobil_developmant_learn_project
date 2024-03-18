package com.lq.favouriteartswithnavigationandfragment.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lq.favouriteartswithnavigationandfragment.ArtModel
import com.lq.favouriteartswithnavigationandfragment.databinding.RecyclerItemBinding
import com.lq.favouriteartswithnavigationandfragment.favouriArtsFragmentDirections.actionFavouriArtsFragmentToDetailArtFragment

class RecyclerAdapter(val items : ArrayList<ArtModel>) : Adapter<RecyclerAdapter.AdapterHolder>() {


    class AdapterHolder(val binding: RecyclerItemBinding) : ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
       val binding : RecyclerItemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false);
       return AdapterHolder(binding)
    }

    override fun getItemCount(): Int {
         return items.size
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val index = position;
        holder.binding.recyclerItemId.text = items.get(index).artName;
        holder.binding.recyclerItemId.setOnClickListener(){
            println("items[index] : "+items[index].toString())
            val action = actionFavouriArtsFragmentToDetailArtFragment("old", items[index]);
            Navigation.findNavController(it).navigate(action);
        }
    }
}