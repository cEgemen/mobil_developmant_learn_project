package com.lq.runcatgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lq.runcatgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
     lateinit var  binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        binding.startButton.setOnClickListener{
            val intent = Intent(this@MainActivity,GameActivity::class.java);
            this@MainActivity.startActivity(intent);
            finish();
        }
        binding.scoreButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {

            }


        })
    }



}