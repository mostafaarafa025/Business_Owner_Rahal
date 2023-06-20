package com.example.businessowner

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding:FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.lottiAnim.addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator) {}

                override fun onAnimationEnd(p0: Animator) {
                    val token = getTokenFromStorage()
                view?.findNavController()?.navigate(R.id.action_splashFragment_to_loginFragment)

//                    if (token.isNotEmpty()) {
//                        // User is logged in
//                        var intent=Intent(requireContext(),InsightsActivity::class.java)
//                        intent.putExtra("token", token)
//                            startActivity(intent)
//                    } else {
//                        // User is not logged in
//                        view?.findNavController()?.navigate(R.id.action_splashFragment_to_loginFragment)
//                    }
                }

                override fun onAnimationCancel(p0: Animator) {}

                override fun onAnimationRepeat(p0: Animator) {}
            })
    }

    private fun getTokenFromStorage(): String {
        val sharedPreferences = requireContext().getSharedPreferences("YourPrefsName", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", "") ?: ""
    }



}