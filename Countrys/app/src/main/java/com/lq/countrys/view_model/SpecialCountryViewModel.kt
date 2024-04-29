package com.lq.countrys.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lq.countrys.model.CountryModel
import com.lq.countrys.service.CountryDatabase
import com.lq.countrys.view.SpecialCountry
import kotlinx.coroutines.launch

class SpecialCountryViewModel(app : Application) : BaseViewModel(app) {
       lateinit var  specialCountry:  MutableLiveData<CountryModel>;

        fun initView(id : Int)
        {
            launch {
                  specialCountry.value =  CountryDatabase.init(getApplication()).countryDAO().getOneCountry(id);
            }
        }


}