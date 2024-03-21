package com.lq.cryptoappwithkotlin.utulitys

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lq.cryptoappwithkotlin.R
import com.lq.cryptoappwithkotlin.databinding.RecyclerItemBinding
import com.lq.cryptoappwithkotlin.modal.CryptoModal

class RecyclerAdapter(val items : ArrayList<CryptoModal>,val colorArr:List<String>) : RecyclerView.Adapter<RecyclerAdapter.AdapterHolder>() {

      class AdapterHolder (val binding : RecyclerItemBinding) : ViewHolder(binding.root)
      {

      }

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
          val binding : RecyclerItemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false);
          return AdapterHolder(binding);
      }

      override fun getItemCount(): Int {
           return items.size
      }

      override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
            holder.itemView.setBackgroundColor(Color.parseColor(colorArr.get(position % 8)))
            holder.binding.cryptoNameTextView.text = "CRYPTO : ${items[position].currency}";
            holder.binding.cryptoPriceTextView.text ="PRICE : ${items[position].price}";
            holder.binding.imageView.setImageResource(R.drawable.crypto)
            holder.itemView.setOnClickListener {
                  AlertDialog.Builder(holder.itemView.context)
                        .setTitle("CRYPTO : ${items[position].currency}")
                        .setMessage("Price : ${items[position].price}")
                        .setNegativeButton("CLOSE") { dialogInterface, i ->
                               dialogInterface.cancel();
                         }
                        .show();
            }
      }
}