package com.example.businessowner.Ui.Insights.Signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {
    lateinit var binding:FragmentForgetPasswordBinding
    private lateinit var sendMailButton: Button
    private lateinit var backArrowButton: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentForgetPasswordBinding.inflate(inflater,container,false)
        intilaizeVariables()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }

        sendMailButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.verificationCodeFragment)
        }

    }
    private fun intilaizeVariables(){
        backArrowButton = binding.backArrowButton
        sendMailButton = binding.sendMailButton
    }

}