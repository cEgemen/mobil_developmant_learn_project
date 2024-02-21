package com.lq.coins;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Observable<List<CoinModel>> getAll();

}
