package com.example.businessowner.Ui.Insights.Signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.businessowner.databinding.FragmentPasswordChangedOrFailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangedOrFailFragment : Fragment() {
    lateinit var binding:FragmentPasswordChangedOrFailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentPasswordChangedOrFailBinding.inflate(inflater,container,false)
        return binding.root
    }


}