package com.lq.kotlintest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lq.kotlintest.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding;
    private lateinit var  intent : Intent;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater);
        val view =binding.root;
        setContentView(view);

        binding.startButton.setOnClickListener {
               intent = Intent(this@HomeActivity,MainActivity::class.java);
               startActivity(intent);
        }
    }




}