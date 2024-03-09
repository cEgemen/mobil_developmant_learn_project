package com.lq.informatincitieswithkotlin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lq.informatincitieswithkotlin.databinding.RecyclerItemBinding

class RecyclerAdapter(val items : ArrayList<CityModel>) : RecyclerView.Adapter<AdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false);
        return AdapterHolder(binding);
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
           val index : Int =position
           holder.binding.cityNameTextView.text = items.get(index).name;
           holder.itemView.setOnClickListener(object : View.OnClickListener{
               override fun onClick(p0: View?) {
                    val selectCity = items.get(index);
                    SingletonCity.city = selectCity;
                    val intent = Intent(holder.itemView.context,DetailActivity::class.java);
                    holder.itemView.context.startActivity(intent);
               }

           })
    }


}

class AdapterHolder(val binding : RecyclerItemBinding) : ViewHolder(binding.root) {

}