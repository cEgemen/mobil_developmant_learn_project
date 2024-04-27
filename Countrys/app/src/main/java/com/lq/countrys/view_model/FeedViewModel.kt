package com.lq.countrys.view_model

import  androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lq.countrys.model.CountryModel
import com.lq.countrys.service.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {
       val apiService = APIService();
       private val dispose : CompositeDisposable = CompositeDisposable();
       lateinit var progressState : MutableLiveData<Boolean> ;
       lateinit var countryList : MutableLiveData<List<CountryModel>>;
       lateinit var errorTextState : MutableLiveData<Boolean>;

    fun refData()
    {

        progressState.value = true;
        dispose.add(
            apiService.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>(){
                    override fun onSuccess(t: List<CountryModel>) {
                         countryList.value = t;
                         errorTextState.value = false;
                    }

                    override fun onError(e: Throwable) {
                        errorTextState.value = true;                    }

                })
        )
        progressState.value = false;

    }
}