package com.lq.coins;

import com.google.gson.annotations.SerializedName;

public class CoinModel {

    @SerializedName("currency")
    String coinName;

    @SerializedName("price")
    String coinPair;


}
