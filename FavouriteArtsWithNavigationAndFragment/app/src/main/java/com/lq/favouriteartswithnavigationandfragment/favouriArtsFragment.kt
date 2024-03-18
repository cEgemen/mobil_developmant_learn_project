package com.lq.favouriteartswithnavigationandfragment

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.lq.favouriteartswithnavigationandfragment.databinding.FragmentFavouriArtsBinding
import com.lq.favouriteartswithnavigationandfragment.recyclerAdapter.RecyclerAdapter

class favouriArtsFragment : Fragment() {
    final lateinit var  binding : FragmentFavouriArtsBinding;
    private lateinit var  db : SQLiteDatabase;
    private var arts : ArrayList<ArtModel> =ArrayList<ArtModel>();
    private lateinit var adapter : RecyclerAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = this.requireContext().openOrCreateDatabase("ARTS", Activity.MODE_PRIVATE,null)
        db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY,artName VARCHAR,artistName VARCHAR,year VARCHAR,img BLOB)")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentFavouriArtsBinding.inflate(layoutInflater,container,false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    fun fetchData()
    {
        var cursor = db.rawQuery("SELECT * FROM arts",null);
        val nameIndex = cursor.getColumnIndex("artName");
        val  idIndex = cursor.getColumnIndex("id");
          while(cursor.moveToNext())
          {
              arts.add(ArtModel(cursor.getString(nameIndex),cursor.getInt(idIndex)))
          }
        cursor.close();
         binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext());
         adapter = RecyclerAdapter(arts);
         binding.recyclerView.adapter = adapter;
    }

}