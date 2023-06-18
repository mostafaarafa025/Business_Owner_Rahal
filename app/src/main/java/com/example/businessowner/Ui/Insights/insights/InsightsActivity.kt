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


        val receivedIndex = intent.getIntExtra("countIndex",0)
        val receiverId=intent.getStringExtra("resId")
        Log.e("activity",receivedIndex.toString())
        val bottomNavigation = binding.bottomNavigation
         navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigation.setupWithNavController(navController)



    }


    private fun navigateToInsightsFragment() {
        val insightsFragment = InsightsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.insight_activity_ID, insightsFragment)
            .commit()
    }
}

