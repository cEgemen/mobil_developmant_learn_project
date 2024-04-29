package com.lq.countrys.utility

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class CustomSharedPrefernces {

     companion object {
         @Volatile
         private var instance : CustomSharedPrefernces? = null;
         private var shared : SharedPreferences? = null;
         val SHARED_NAME : String = "COUNTRYSHARED";
         val SHARED_TIME : String = "TIME";
         private val lock = Any();
         fun init(context : Context) : CustomSharedPrefernces {
             return instance?: synchronized(lock){
                   context.getSharedPreferences(SHARED_NAME,MODE_PRIVATE).also {
                       shared = it;
                  }
                  return CustomSharedPrefernces();
             }
         }


     }
    fun storeTime(time : Long)
    {
        val edit = shared?.edit().also {

            it?.putLong(SHARED_TIME,time);
            it?.apply();
            it?.commit();
        }
    }
    fun getTime() = shared?.getLong(SHARED_TIME,0);
}