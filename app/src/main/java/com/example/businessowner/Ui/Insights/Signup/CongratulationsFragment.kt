package com.example.businessowner.Ui.Insights.Signup

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentConguratulationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CongratulationsFragment : Fragment() {
    private val delayDuration = 5000L // Delay in milliseconds

    lateinit var binding:FragmentConguratulationsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding= FragmentConguratulationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()
        handler.postDelayed({
            navigateToSecondFragment()
        }, delayDuration)
    }
    private fun navigateToSecondFragment() {
        findNavController().navigate(R.id.action_congratulationsFragment_to_loginFragment)
    }
}