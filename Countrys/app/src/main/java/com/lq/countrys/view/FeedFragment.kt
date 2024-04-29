package com.lq.countrys.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lq.countrys.adapters.CountryRecyclerAdapter
import com.lq.countrys.databinding.FragmentFeedBinding
import com.lq.countrys.view_model.FeedViewModel


class FeedFragment : Fragment() {
    lateinit var  viewModel : FeedViewModel ;
    lateinit var  binding : FragmentFeedBinding;
    val adapter : CountryRecyclerAdapter = CountryRecyclerAdapter(listOf());

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = FeedViewModel(Application());
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java);
        binding.feedRefreshLayout.setOnRefreshListener {
              binding.feedRecyclerView.visibility = View.GONE;
            binding.feedTextError.visibility = View.GONE;
            binding.feedProgressBar.visibility = View.VISIBLE;
            binding.feedRefreshLayout.isRefreshing = false;
              viewModel.refresh()

        }
        binding.feedRecyclerView.adapter = adapter;
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(view.context);
        viewModel.refData();
        obserDatas();
    }

    fun obserDatas()
    {
         viewModel.countryList.observe(viewLifecycleOwner, Observer {counties ->
                 counties?.let {
                         adapter.updateRecylerData(it);
                        viewModel.progressState.value = false;
                        viewModel.errorTextState.value = false;
                 }
         });
        viewModel.progressState.observe(viewLifecycleOwner, Observer { state ->
                 state?.let {
                        if(state)
                        {
                            binding.feedRecyclerView.visibility = View.GONE
                            binding.feedProgressBar.visibility = View.VISIBLE
                        }
                     else{
                           binding.feedProgressBar.visibility = View.GONE
                        }
                 }
        });

        viewModel.errorTextState.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                if(state)
                {
                    binding.feedRecyclerView.visibility = View.GONE
                    binding.feedTextError.visibility = View.GONE
                }
                else{
                    binding.feedTextError.visibility = View.VISIBLE
                }
            }
        })
    }



}