package com.lq.cryptoappwithcompose.service

import com.lq.cryptoappwithcompose.modal.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(value = "atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getAllCrypto() : Call<List<CryptoModel>>;

}