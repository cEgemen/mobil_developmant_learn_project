package com.lq.myfavoritelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lq.myfavoritelocation.RoomDatabase.FavoriLocation;
import com.lq.myfavoritelocation.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
    RecycleViewAdapter adapter;

    ArrayList<FavoriLocation> locations = new ArrayList<FavoriLocation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new RecycleViewAdapter(locations) ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("type","newLocation");
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}