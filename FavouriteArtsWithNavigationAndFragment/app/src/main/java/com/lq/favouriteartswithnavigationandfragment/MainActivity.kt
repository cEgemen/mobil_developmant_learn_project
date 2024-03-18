package com.lq.favouriteartswithnavigationandfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import com.lq.favouriteartswithnavigationandfragment.databinding.ActivityMainBinding
import com.lq.favouriteartswithnavigationandfragment.favouriArtsFragmentDirections.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addId)
        {
          val action = actionFavouriArtsFragmentToDetailArtFragment("new", ArtModel("",-1))
            Navigation.findNavController(this.binding.fragmentContainerView).navigate(action);
        }
        return super.onOptionsItemSelected(item)
    }

}