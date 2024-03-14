package com.lq.favoriteartswithkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.lq.favoriteartswithkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        lateinit var binding :ActivityMainBinding ;
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if(item.itemId == R.id.menuItem)
         {
             val intent : Intent = Intent(this@MainActivity,ArtDetailAcrivity::class.java);
             intent.putExtra("type","new");
             startActivity(intent);
             finish()
         }
        return super.onOptionsItemSelected(item)
    }
}