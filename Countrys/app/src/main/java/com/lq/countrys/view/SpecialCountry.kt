package com.lq.countrys.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lq.countrys.R
import com.lq.countrys.databinding.FragmentFeedBinding
import com.lq.countrys.databinding.FragmentSpecialCountryBinding
import com.lq.countrys.utility.getImage
import com.lq.countrys.view_model.FeedViewModel
import com.lq.countrys.view_model.SpecialCountryViewModel

class SpecialCountry : Fragment() {
      lateinit var  binding : FragmentSpecialCountryBinding ;
      lateinit var  viewModel : SpecialCountryViewModel;
      private var id : Int = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialCountryBinding.inflate(inflater);
        arguments?.let {
            id= SpecialCountryArgs.fromBundle(it).id;
        }
        viewModel = ViewModelProvider(this).get(SpecialCountryViewModel::class.java);
        viewModel.initView(id)
        observeData()
        return binding.root
    }

     fun observeData()
     {
          viewModel.specialCountry.observe(viewLifecycleOwner) {
                  binding.countryName.text  = it.countryName;
                  binding.countryRegian.text = it.countryRegion;
                  binding.countryCapital.text = it.countryCapital;
                  binding.countryCurrency.text = it.countryCurrency;
                  binding.countryLanguage.text = it.countryLanguage;
                  context?.let {context ->
                      binding.countryImage.getImage(it.countryImg,context)
                  }
          }
     }

}
