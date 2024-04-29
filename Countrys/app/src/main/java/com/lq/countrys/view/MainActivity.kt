package com.lq.countrys.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.lq.countrys.R
import com.lq.countrys.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  navController : NavController ;
    lateinit var  binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        navController = findNavController(R.id.fragmentContainerView)
        NavigationUI.setupActionBarWithNavController(this,navController,null);

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null);
    }

}