package com.example.businessowner.Ui.Insights.insights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.businessowner.R
import com.example.businessowner.databinding.ActivityInsightsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightsActivity : AppCompatActivity() {
    lateinit var binding: ActivityInsightsBinding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val receivedIndexRes = intent.getStringExtra("countIndexRes")
        val receiverIdRes=intent.getStringExtra("resId")
        val token=intent.getStringExtra("token")

        val receivedIndexHotel=intent.getStringExtra("countIndexHotel")
        val receiverIdHotel=intent.getStringExtra("hotelId")
        Log.e("indexRes",receivedIndexRes.toString())
        val bottomNavigation = binding.bottomNavigation
         navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigation.setupWithNavController(navController)


    }

}

