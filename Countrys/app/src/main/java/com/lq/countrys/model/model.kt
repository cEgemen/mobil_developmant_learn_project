package com.lq.countrys.model

import com.google.gson.annotations.SerializedName

data class CountryModel(
     @SerializedName("name")
     val countryName : String,
     @SerializedName("capital")
     val countryCapital : String,
     @SerializedName("region")
     val countryRegion : String,
     @SerializedName("currency")
     val countryCurrency : String,
     @SerializedName("flag")
     val countryImg : String
     );