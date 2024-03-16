package com.lq.favouriteplaceswithkotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Place::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

}