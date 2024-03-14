package com.lq.favoriteartswithkotlin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lq.favoriteartswithkotlin.databinding.RecyclerItemBinding

class RecyclerAdapter(val items : ArrayList<ArtModel>) : Adapter<AdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
         val binding : RecyclerItemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false);
        return AdapterHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
              val index :Int = position;
              holder.binding.artItemTextView.text = items.get(index).artName;
              holder.itemView.setOnClickListener(){
                      val intent : Intent = Intent(holder.itemView.context,ArtDetailAcrivity::class.java);
                      intent.putExtra("type","old");
                      intent.putExtra("id",items.get(index).id)
                     holder.itemView.context.startActivity(intent);
              }
    }


}

class AdapterHolder(val binding : RecyclerItemBinding) : ViewHolder(binding.root) {


}