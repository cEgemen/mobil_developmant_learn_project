package com.lq.countrys.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lq.countrys.model.CountryModel

@Database(entities = arrayOf(CountryModel::class), version = 1)
abstract class CountryDatabase : RoomDatabase(){

    abstract fun countryDAO() : CountryDAO;

    companion object {
        @Volatile
        var instance  : CountryDatabase? = null;
        val lock = Any();

        fun init(context : Context) : CountryDatabase {
            return instance ?: synchronized(lock){
                    val newDB = initDatabase(context).also {
                         instance = it;
                    }
                return newDB;
            }

        }

        private fun initDatabase(context : Context) : CountryDatabase{
           return Room.databaseBuilder(context.applicationContext,CountryDatabase::class.java,"CountryDatabase").build();
        }

    }

}