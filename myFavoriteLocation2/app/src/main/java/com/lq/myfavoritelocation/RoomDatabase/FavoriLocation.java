package com.lq.myfavoritelocation.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FavoriLocation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id ;
    @ColumnInfo(name = "locationName")
  public  String locationName;
    @ColumnInfo(name = "latitude")
  public  double latitude;

    @ColumnInfo(name = "longitude")
  public  double longitude;

   public FavoriLocation(String locationName, double longitude, double latitude)
   {
       this.locationName = locationName;
       this.longitude = longitude;
       this.latitude = latitude;
   }

}
