package com.example.businessowner.Ui.Insights.insights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        val bottomNavigation = binding.bottomNavigation
         navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
        if(
            destination.id !=R.id.insightsFragment &&
            destination.id !=R.id.reviewsFragment &&
            destination.id !=R.id.profileFragment2
        ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            bottomNavigation.visibility=View.GONE
        }else
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            bottomNavigation.visibility=View.VISIBLE
        }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

