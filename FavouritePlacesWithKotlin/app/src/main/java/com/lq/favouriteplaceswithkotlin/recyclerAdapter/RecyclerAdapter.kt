package com.lq.favouriteplaceswithkotlin.recyclerAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lq.favouriteplaceswithkotlin.MapsActivity
import com.lq.favouriteplaceswithkotlin.database.Place
import com.lq.favouriteplaceswithkotlin.databinding.RecyclerItemBinding
import com.lq.favouriteplaceswithkotlin.singlation.SelectPlace

class RecyclerAdapter(val items :List<Place>) : Adapter<RecyclerAdapter.AdapterHolder>() {




    class AdapterHolder(val binding :RecyclerItemBinding) : ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val binding : RecyclerItemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false);
        return AdapterHolder(binding);
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val index = position;
        holder.binding.recyclerItemId.text = items.get(index).placeName;
        holder.itemView.setOnClickListener {
               val intent : Intent = Intent(it.context, MapsActivity::class.java);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
               SelectPlace.selectPlace = items.get(index);
              it.context.startActivity(intent);
        }
    }
}