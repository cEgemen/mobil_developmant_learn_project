package com.lq.countrys.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lq.countrys.model.CountryModel
import com.lq.countrys.view.SpecialCountry

class SpecialCountryViewModel : ViewModel() {
       lateinit var  specialCountry:  MutableLiveData<CountryModel>;


        fun initView()
        {
              specialCountry.value = CountryModel("Turkey","Ankara","Asia","TRY","");
        }
}