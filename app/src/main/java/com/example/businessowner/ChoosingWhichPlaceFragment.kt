package com.example.businessowner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.adapters.ChoosePlaceAdapter
import com.example.businessowner.databinding.FragmentChoosingWhichPlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosingWhichPlaceFragment : Fragment() {
    private val requestViewModel:RequestViewModel by viewModels()
    lateinit var binding:FragmentChoosingWhichPlaceBinding
    private lateinit var choosePlaceAdapter: ChoosePlaceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        choosePlaceAdapter= ChoosePlaceAdapter(requestViewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentChoosingWhichPlaceBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        requestViewModel.getRestaurantResponse()
        requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner){ it->
            choosePlaceAdapter.differ.submitList(it)
        }
        choosePlaceAdapter.onItemClickListener={restaurant , position ->
            val countIndex=position
            val resId=restaurant.id
            Log.d("ItemClicked", "Position: $position")
            val intent= Intent(requireContext(), InsightsActivity::class.java)
            intent.putExtra("countIndex",countIndex )
            intent.putExtra("resId",resId)
            startActivity(intent)
        }
    }
    private fun setUpRecyclerView(){
        binding.recyclerViewPlace.apply {
            adapter=choosePlaceAdapter
        }
    }

}