package com.lq.favouriteplaceswithkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.lq.favouriteplaceswithkotlin.database.Place
import com.lq.favouriteplaceswithkotlin.database.PlaceDao
import com.lq.favouriteplaceswithkotlin.database.RoomDB
import com.lq.favouriteplaceswithkotlin.databinding.ActivityMainBinding
import com.lq.favouriteplaceswithkotlin.recyclerAdapter.RecyclerAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var  binding : ActivityMainBinding;
    private lateinit var db : RoomDB;
    private lateinit var dao: PlaceDao;
    private  var placesItem : List<Place> = listOf();
    private lateinit var adapter : RecyclerAdapter;
    private val composite : CompositeDisposable = CompositeDisposable();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        adapter = RecyclerAdapter(placesItem);
        binding.recyclerView.layoutManager = LinearLayoutManager(this);
        binding.recyclerView.adapter = adapter;
        getFetch()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          if(item.itemId  == R.id.menuItemId)
          {
              val intent : Intent = Intent(this@MainActivity,MapsActivity::class.java)
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
               intent.putExtra("type","new")
              startActivity(intent);

          }
        return super.onOptionsItemSelected(item)
    }

    fun getFetch()
    {
        db =  Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "Places"
        ).build()
        dao = db.placeDao();
        composite.add(  dao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handelRes))
    }

    fun handelRes (items : List<Place>)
    {
        placesItem = items;
        adapter = RecyclerAdapter(placesItem);
        binding.recyclerView.adapter = adapter;

    }
    override fun onDestroy() {
        super.onDestroy()
       composite.clear();
    }
}
