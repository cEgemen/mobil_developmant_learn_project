package com.lq.countrys.service

import com.lq.countrys.model.CountryModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIService  {
    private val BASE_URL : String ="https://raw.githubusercontent.com/";
    private val OTHER_PART_URL : String = "atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json";
    private val iAPI  = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(IAPIService::class.java);
    fun getAllCountries() : Single<List<CountryModel>>
    {
         return iAPI.getCountries()
    }

}