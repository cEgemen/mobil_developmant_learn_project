package com.lq.runcatgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.lq.runcatgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
     lateinit var  binding : ActivityMainBinding;
     val shared : DataStore = DataStore.getInitStore();
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
                  AlertDialog.Builder(this@MainActivity)
                      .setTitle("HIGH SCORE")
                      .setMessage("High Score : "+shared.getHightScore(this@MainActivity))
                      .setNegativeButton("OK",{dialog,i -> dialog.cancel()}).show();
            }


        })
    }



}