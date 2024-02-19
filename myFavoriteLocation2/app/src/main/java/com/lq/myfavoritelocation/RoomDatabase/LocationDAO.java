package com.lq.myfavoritelocation.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface LocationDAO {
    @Query("SELECT * FROM FavoriLocation")
    Flowable<ArrayList<FavoriLocation>> getAll();

  @Insert
  Completable insert(FavoriLocation favoriLocation);

    @Delete
    Completable delete(FavoriLocation location);
}
