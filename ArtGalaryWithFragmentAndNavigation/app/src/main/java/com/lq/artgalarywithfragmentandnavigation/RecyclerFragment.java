package com.lq.artgalarywithfragmentandnavigation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lq.artgalarywithfragmentandnavigation.databinding.RecyclerFragmentBinding;

import java.util.ArrayList;

public class RecyclerFragment extends Fragment {
    RecyclerFragmentBinding binding;
    SQLiteDatabase db;
    ArrayList<ArtModel> arts ;
    RecyclerAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecyclerFragmentBinding.inflate(getLayoutInflater());
        arts = new ArrayList<ArtModel>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchData(view.getContext());


    }

    void fetchData(Context context)
    {
        db = context.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS  arts (id INTEGER PRIMARY KEY,name VARCHAR,image BLOB)");
        Cursor cursor = db.rawQuery("SELECT * FROM arts",null);
        int nameIndex = cursor.getColumnIndex("name");
        int idIndex = cursor.getColumnIndex("id");
        int imageIndex = cursor.getColumnIndex("image");
        while(cursor.moveToNext())
        {
            arts.add(new ArtModel("new",cursor.getString(nameIndex),cursor.getInt(idIndex),cursor.getBlob(imageIndex)));
        }
        cursor.close();
        adapter = new RecyclerAdapter(arts);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.setAdapter(adapter);
    }
}
