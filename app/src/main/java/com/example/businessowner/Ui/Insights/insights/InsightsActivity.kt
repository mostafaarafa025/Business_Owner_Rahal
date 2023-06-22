package com.example.businessowner.Ui.Insights.insights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.businessowner.R
import com.example.businessowner.databinding.ActivityInsightsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightsActivity : AppCompatActivity() {
    lateinit var binding: ActivityInsightsBinding
    private lateinit var navController:NavController
    private val backStack = mutableListOf<Int>()
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


        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val currentFragmentId = getCurrentFragmentId()

            if (currentFragmentId != item.itemId) {
                if (item.itemId == R.id.profileFragment2) {
                    clearBackStack()
                }
                navController.navigate(item.itemId)
            }

            true
        }


    }

    private fun getCurrentFragmentId(): Int {
        val currentDestination = navController.currentDestination
        return currentDestination?.id ?: 0
    }

    private fun clearBackStack() {
        val graph = navController.navInflater.inflate(R.navigation.nav_host_insights)
        val destination = graph.findNode(R.id.profileFragment2) as NavDestination
        navController.popBackStack(destination.id, true)
    }

}

