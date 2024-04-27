package com.lq.countrys.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lq.countrys.databinding.RecyclerLayoutBinding
import com.lq.countrys.model.CountryModel
import com.lq.countrys.utility.getImage
import com.lq.countrys.view.FeedFragmentDirections

class CountryRecyclerAdapter(var countryList : List<CountryModel>) : RecyclerView.Adapter<CountryRecyclerAdapter.CountryRecyclerAdapterViewHolder>(){

     class CountryRecyclerAdapterViewHolder(val binding  : RecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root){

     }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryRecyclerAdapterViewHolder {
         val binding  =  RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false);
         return CountryRecyclerAdapterViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return countryList.size;
    }

    override fun onBindViewHolder(holder: CountryRecyclerAdapterViewHolder, position: Int) {
           holder.binding.recyclerName.text = countryList[position].countryName;
           holder.binding.recyclerRegion.text = countryList[position].countryRegion;
           holder.itemView.setOnClickListener {
                 val action  = FeedFragmentDirections.actionFeedFragmentToSpecialCountry();
                 Navigation.findNavController(it).navigate(action);
           }
          holder.binding.recyleImage.getImage(countryList[position].countryImg,holder.itemView.context);
    }

     fun updateRecylerData( newCountryData : List<CountryModel>)
     {
           countryList = newCountryData;
           notifyDataSetChanged();
     }

}