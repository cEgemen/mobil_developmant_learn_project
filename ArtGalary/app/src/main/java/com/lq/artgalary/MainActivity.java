package com.lq.artgalary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lq.artgalary.databinding.ActivityArtActionBinding;
import com.lq.artgalary.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     ActivityMainBinding binding ;
     ArrayList<ArtModel> arts = new ArrayList<ArtModel>();
     SQLiteDatabase db;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
          fetchData();
        adapter = new RecyclerViewAdapter(arts);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       Intent intent = new Intent(MainActivity.this, artActionActivity.class);
       intent.putExtra("type","new");
       startActivity(intent);
       return super.onOptionsItemSelected(item);
   }

   public void fetchData()
   {
       try{
           db = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
           db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY,name VARCHAR" +
                   ",artist VARCHAR,year VARCHAR,image BLOB)");
           Cursor cursor = db.rawQuery("SELECT * FROM arts",null);
           while(cursor.moveToNext())
           {
                arts.add(new ArtModel(cursor.getString(1),123));
           }
           cursor.close();
           System.out.println("arts.size() => "+arts.size());
       }
       catch (Exception e)
       {
          System.out.println("errrorrrr");
       }
   }


}