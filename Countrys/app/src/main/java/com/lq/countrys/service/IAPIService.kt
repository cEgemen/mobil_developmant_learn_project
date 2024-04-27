package com.lq.countrys.service

import com.lq.countrys.model.CountryModel
import io.reactivex.Single
import retrofit2.http.GET

interface IAPIService {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<CountryModel>>

}