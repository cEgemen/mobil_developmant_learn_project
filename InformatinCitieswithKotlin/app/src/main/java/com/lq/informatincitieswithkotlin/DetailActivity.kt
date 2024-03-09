package com.lq.informatincitieswithkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lq.informatincitieswithkotlin.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var  binding : ActivityDetailBinding ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val selectCity : CityModel = SingletonCity.city?:CityModel("Null","Null",R.drawable.ic_launcher_background);
         binding.cityImageView.setImageResource(selectCity.img);
        binding.descTextView.text=selectCity.desc;
        binding.nameTextView.text = selectCity.name;
    }

    fun back(view : View){
                 val intent = Intent(this@DetailActivity,MainActivity::class.java);
                 this.startActivity(intent);
                 finish();
    }

}