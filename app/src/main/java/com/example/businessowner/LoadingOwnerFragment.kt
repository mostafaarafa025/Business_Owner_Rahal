package com.example.businessowner

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.databinding.FragmentLoadingOwnerBinding

class LoadingOwnerFragment : Fragment() {
    lateinit var binding:FragmentLoadingOwnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentLoadingOwnerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            val intent=Intent(requireContext(), InsightsActivity::class.java)
            startActivity(intent)
        }
    }

}