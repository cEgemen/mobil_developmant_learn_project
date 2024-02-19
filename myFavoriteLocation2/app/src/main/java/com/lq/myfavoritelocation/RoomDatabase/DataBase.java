package com.lq.myfavoritelocation.RoomDatabase;

import androidx.room.rxjava3.RxRoom;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriLocation.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract LocationDAO locationDAO();
}