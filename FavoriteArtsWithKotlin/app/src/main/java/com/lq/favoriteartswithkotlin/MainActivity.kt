package com.lq.favoriteartswithkotlin

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.lq.favoriteartswithkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        lateinit var binding :ActivityMainBinding ;
         lateinit var  db : SQLiteDatabase;
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
            db = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null);
            val adapter : RecyclerAdapter = RecyclerAdapter(fetchDatas());
            binding.recyclerView.layoutManager = LinearLayoutManager(this);
            binding.recyclerView.adapter = adapter;
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

    fun fetchDatas() : ArrayList<ArtModel> {
        val datas :ArrayList<ArtModel> = ArrayList<ArtModel>();
        db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY , artName VARCHAR,artistName VARCHAR,year VARCHAR,img BLOB)");
        val cursor = db.rawQuery("SELECT * FROM arts",null);
        val nameIndex = cursor.getColumnIndex("artName");
        val idIndex = cursor.getColumnIndex("id");
              while(cursor.moveToNext())
              {
                 datas.add(ArtModel(cursor.getString(nameIndex),cursor.getInt(idIndex)))
              }
              cursor.close();
        return datas;
    }

}