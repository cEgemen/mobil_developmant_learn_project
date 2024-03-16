package com.lq.favouriteplaceswithkotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getAll() : Flowable<List<Place>>

    @Insert
    fun insertPlace(place : Place) : Completable

    @Delete
    fun deletePlace(place : Place) : Completable

}