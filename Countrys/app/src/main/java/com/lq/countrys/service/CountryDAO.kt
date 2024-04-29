package com.lq.countrys.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lq.countrys.model.CountryModel

@Dao
interface CountryDAO {

     @Query("SELECT * FROM CountryModel")
     suspend fun  getAllCountries() : List<CountryModel>

     @Query("SELECT * FROM CountryModel WHERE uuid = :id")
     suspend fun  getOneCountry(id : Int) : CountryModel

     @Insert
     suspend fun  insertAllCountries(vararg countries : CountryModel) : List<Long>

     @Query("DELETE FROM CountryModel")
     suspend fun  deleteAllCountries();

     @Query("DELETE FROM CountryModel WHERE uuid = :id")
     suspend fun  deleteCountry(id : Int) : CountryModel;

}