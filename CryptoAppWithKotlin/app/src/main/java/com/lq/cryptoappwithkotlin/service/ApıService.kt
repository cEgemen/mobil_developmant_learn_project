package com.lq.cryptoappwithkotlin.service

import com.lq.cryptoappwithkotlin.modal.CryptoModal
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
        @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
        suspend fun getData() : List<CryptoModal>
    }
