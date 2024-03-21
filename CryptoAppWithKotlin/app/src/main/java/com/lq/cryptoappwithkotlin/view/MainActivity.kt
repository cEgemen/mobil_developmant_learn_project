package com.lq.cryptoappwithkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Converter.Factory
import androidx.recyclerview.widget.LinearLayoutManager
import com.lq.cryptoappwithkotlin.databinding.ActivityMainBinding
import com.lq.cryptoappwithkotlin.modal.CryptoModal
import com.lq.cryptoappwithkotlin.service.ApiService
import com.lq.cryptoappwithkotlin.utulitys.RecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    var items: ArrayList<CryptoModal>? = null;
    private lateinit var job: Job;
    lateinit var adapter: RecyclerAdapter;
    private val BASE_URL: String = "https://raw.githubusercontent.com/"
    private val colors = arrayListOf<String>(
        "#a6f5c5",
        "#fb7105",
        "#727cf5",
        "#94f667",
        "#d0cece",
        "#0a225b",
        "#d96ad5",
        "#dcd1b4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root);
        fetchData()
    }


    fun fetchData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
         val apiService = retrofit.create(ApiService::class.java);

         job = CoroutineScope(Dispatchers.IO).launch {
                apiService.getData().also {
                           withContext(Dispatchers.Main){
                               adapter = RecyclerAdapter(ArrayList<CryptoModal>(it),colors);
                               binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity);
                               binding.recyclerView.adapter = adapter;
                           }
                }
         }



    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}