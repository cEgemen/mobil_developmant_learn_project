package com.lq.coins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lq.coins.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String BASE_URL = "https://raw.githubusercontent.com/";
    ArrayList<CoinModel> coins;
    CompositeDisposable disposable;
    ApiService service;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        disposable = new CompositeDisposable();

        Gson gson =new GsonBuilder()
                .setLenient()
                .create();

        retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

         service = retrofit.create(ApiService.class);


        fetchData();


    }


    public void fetchData()
    {
        try{
            disposable.add( service.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handelResponse)
            );

        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void handelResponse(List<CoinModel> items)
    {
        coins = new ArrayList<CoinModel>(items);
        RecyclerAdapter adapter = new RecyclerAdapter(coins);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}