package com.lq.countrys.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lq.countrys.R
import com.lq.countrys.databinding.FragmentFeedBinding

class SpecialCountry : Fragment() {
      lateinit var  binding : FragmentFeedBinding ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater);
        return binding.root
    }

}
