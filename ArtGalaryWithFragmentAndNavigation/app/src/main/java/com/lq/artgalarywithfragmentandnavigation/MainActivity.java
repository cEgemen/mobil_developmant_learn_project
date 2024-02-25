package com.lq.artgalarywithfragmentandnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lq.artgalarywithfragmentandnavigation.RecyclerFragmentDirections.ActionRecyclerFragmentToArtAddFragment;
import com.lq.artgalarywithfragmentandnavigation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         RecyclerFragmentDirections.ActionRecyclerFragmentToArtAddFragment action;
         action = RecyclerFragmentDirections.actionRecyclerFragmentToArtAddFragment(new ArtModel("new",null,-1));
         if(item.getActionView() != null)
         {
             Navigation.findNavController(item.getActionView()).navigate(action);
         }
        return true;
    }
}