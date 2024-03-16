package com.lq.favouriteplaceswithkotlin.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Place(
    @ColumnInfo(name = "name")
    val placeName:String,
    @ColumnInfo("lat")
    val placeLat : Double,
    @ColumnInfo("lot")
    val placeLot : Double
)  {
    @PrimaryKey(autoGenerate = true)
    var id = 0 ;
}