package com.lq.myfavoritelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lq.myfavoritelocation.RoomDatabase.DataBase;
import com.lq.myfavoritelocation.RoomDatabase.FavoriLocation;
import com.lq.myfavoritelocation.RoomDatabase.LocationDAO;
import com.lq.myfavoritelocation.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
    RecycleViewAdapter adapter;
    DataBase db;
    LocationDAO dao;
    ArrayList<FavoriLocation> locations = new ArrayList<FavoriLocation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db =  Room.databaseBuilder(getApplicationContext(), DataBase.class,"FavoriLocation").build();
        dao = db.locationDAO();
        fetchData();
        adapter = new RecycleViewAdapter(locations) ;
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("type","newLocation");
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void fetchData()
    {
         dao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::WhenGetData);
    }

    public void WhenGetData(List<FavoriLocation> locations)
    {
       try{
           this.locations.addAll(locations);
       }
       catch (Exception e)
       {
           System.out.println("whenGetData errrorrr");
       }
       adapter.notifyDataSetChanged();
    }


}