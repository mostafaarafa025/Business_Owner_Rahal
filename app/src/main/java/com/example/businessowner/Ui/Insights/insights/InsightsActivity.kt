package com.example.businessowner.Ui.Insights.insights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.businessowner.R
import com.example.businessowner.databinding.ActivityInsightsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightsActivity : AppCompatActivity() {
    lateinit var binding:ActivityInsightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation=binding.bottomNavigation
        val findNavigation= Navigation.findNavController(this,R.id.fragmentContainerView2)
        NavigationUI.setupWithNavController(bottomNavigation,findNavigation)

    }

}