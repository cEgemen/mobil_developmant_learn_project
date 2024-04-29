package com.lq.countrys.view_model

import android.app.Application
import android.widget.Toast
import  androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lq.countrys.model.CountryModel
import com.lq.countrys.service.APIService
import com.lq.countrys.service.CountryDatabase
import com.lq.countrys.utility.CustomSharedPrefernces
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(app : Application) : BaseViewModel(app) {
       private val shared : CustomSharedPrefernces = CustomSharedPrefernces.init(getApplication());
       val apiService = APIService();
       private val dispose : CompositeDisposable = CompositeDisposable();
       lateinit var progressState : MutableLiveData<Boolean> ;
       lateinit var countryList : MutableLiveData<List<CountryModel>>;
       lateinit var errorTextState : MutableLiveData<Boolean>;
       private val time = 10 * 0 *1000 * 1000 * 1000L;

    fun refData()
    {
        val sharetTime : Long? = shared.getTime();
        if (sharetTime != null && sharetTime != 0L && System.nanoTime() - sharetTime < time )
        {
               getDataFromAPI()
        }
        else{
            getFromDatabase()
        }

    }
    fun refresh()
    {
          getDataFromAPI();
    }

   private fun getFromDatabase()
    {
        launch {
            val list = CountryDatabase.init(getApplication()).countryDAO().getAllCountries();
            countryList.value = list;
            errorTextState.value = false;
        }
        Toast.makeText(getApplication(),"FROM SQL",Toast.LENGTH_LONG).show();
    }

  private fun getDataFromAPI()
    {
        progressState.value = true;
        dispose.add(
            apiService.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>(){
                    override fun onSuccess(t: List<CountryModel>) {
                        saveDatabase(t)
                    }
                    override fun onError(e: Throwable) {
                        errorTextState.value = true;                    }

                })
        )
        progressState.value = false;
        Toast.makeText(getApplication(),"FROM API",Toast.LENGTH_LONG).show();

    }
    fun saveDatabase(countries : List<CountryModel>)
    {
        launch {
             val dao = CountryDatabase.init(getApplication()).countryDAO();
             dao.deleteAllCountries();
            val listLong =  dao.insertAllCountries(*countries.toTypedArray());
             var syc = 0 ;
             while(syc < countries.size)
             {
                 countries[syc].uuid = listLong[syc].toInt();
                 syc ++;
             }
             countryList.value = countries;
            errorTextState.value = false;
        }
        shared.storeTime(System.nanoTime());
    }

    override fun onCleared() {
        super.onCleared()
        dispose.clear()
    }

}