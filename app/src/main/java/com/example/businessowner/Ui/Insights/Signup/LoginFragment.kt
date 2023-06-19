package com.example.businessowner.Ui.Insights.Signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.Ui.Insights.viewmodel.AuthViewModel
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.Ui.Insights.viewmodel.SharedViewModel
import com.example.businessowner.databinding.FragmentLoginBinding
import com.example.businessowner.model.authentication.LoginRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var emailEditText: EditText
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var passwordEditText: EditText
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var emailErrorMessage: TextView
    private lateinit var passwordErrorMessage: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeVariables()
        super.onViewCreated(view, savedInstanceState)
        validateEmail()
        validatePassword()


        binding.registerNowTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.signUpFragment)
        }

        binding.forgetPasswordTextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.forgetPasswordFragment)
        }

        binding.floatingButton.setOnClickListener {
            login()
        }
    }

    private fun login() {

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val loginRequest = LoginRequest().apply {
            this.email = email
            this.password = password
        }
        authViewModel.login(loginRequest)
        authViewModel.loginResponse.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                data?.let {
                    Log.e("success", data.data.user.name.toString())
                    Log.e("success", data.data.user.email.toString())
                    Log.e("id", data.data.user.id.toString())
                    Log.e("success", data.data.user.password.toString())
                    Log.e("role", data.data.user.role.toString())
                    Log.e("token", data.token.toString())
                }
//               checkRestaurantResponse()
//              checkHotelResponse()
                checkPlaceResponse()
            } else
                Toast.makeText(activity, "Login Failed ", Toast.LENGTH_LONG).show()
        }
        authViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initializeVariables() {
        emailEditText = binding.emailEditText
        emailErrorMessage = binding.errorMessageValidEmail

        passwordEditText = binding.passwordEditText
        passwordErrorMessage = binding.errorMessagePassword
    }

    private fun showErrorMessage(editText: EditText, textView: TextView, errorMessage: String) {
        editText.error = errorMessage
        textView.text = errorMessage
    }

    private fun validateEmail() {
        val errorMessage = "Enter a valid email"

        emailEditText.doAfterTextChanged {
            if (emailEditText.text.toString().isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()
            ) {
                emailErrorMessage.text = ""
            } else {
                showErrorMessage(emailEditText, emailErrorMessage, errorMessage)
            }
        }
    }

    private fun validatePassword() {
        val errorMessage = "password should be 8 character or more"

        passwordEditText.doAfterTextChanged {
            // if password length is less than 8
            if (passwordEditText.text.toString().length < 8) {
                showErrorMessage(passwordEditText, passwordErrorMessage, errorMessage)
            } else {
                passwordErrorMessage.text = ""
            }
        }
    }

//    private fun checkRestaurantResponse() {
//        requestViewModel.getRestaurantResponse()
//        requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner) { response ->
//            Log.d("restaurant", "Restaurant Response: $response")
//            val resultCount = response.size
//            if (resultCount == 0) {
//                Log.e("noRes", "empty")
//                view?.findNavController()?.navigate(R.id.action_loginFragment_to_signUp1)
//            }
//            if (resultCount == 1) {
//                Log.e("oneRes", "oneRes")
//                val countIndexRes = "0"
//                val resId = response[0].id
//                val intent = Intent(requireContext(), InsightsActivity::class.java)
//                intent.putExtra("countIndexRes", countIndexRes)
//                intent.putExtra("resId", resId)
//                startActivity(intent)
//            } else if (resultCount > 1) {
//                Log.e("placeCount", "more than one restaurant")
//                view?.findNavController()
//                    ?.navigate(R.id.action_loginFragment_to_choosingWhichPlaceFragment)
//            }
//        }
//    }
//
    private fun checkHotelResponse() {
        requestViewModel.getHotelResponse()
        requestViewModel.getHotelResponseLiveData.observe(viewLifecycleOwner) { response ->
            val resultCount = response.size
            if (resultCount == 0) {
                Log.e("noHotel", "empty")
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_signUp1)
            } else if (resultCount == 1) {
                Log.e("oneHotel", "oneHotel")
                val countIndexHotel = "0"
                val hotelId = response[0].id
                val intent = Intent(requireContext(), InsightsActivity::class.java)
                intent.putExtra("countIndexHotel", countIndexHotel)
                intent.putExtra("hotelId", hotelId)
                startActivity(intent)
            } else if (resultCount > 1) {
                Log.e("HotelCount", "more than one hotel")
                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_choosingWhichPlaceFragment)
            }
        }
    }

    private fun checkPlaceResponse() {
        requestViewModel.getRestaurantResponse()
        requestViewModel.getHotelResponse()
        requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner) { restaurantResponse ->
            val restaurantResultCount = restaurantResponse.size
            requestViewModel.getHotelResponseLiveData.observe(viewLifecycleOwner){ hotelResponse ->
                val hotelResultCount = hotelResponse.size

                if (restaurantResultCount == 0 && hotelResultCount == 0) {
                    Log.e("noRes", "empty")
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_signUp1)
                } else if (restaurantResultCount == 1 && hotelResultCount == 0) {
                    Log.e("oneRes", "oneRes")
                    val countIndexRes = "0"
                    val resId = restaurantResponse[0].id
                    val intent = Intent(requireContext(), InsightsActivity::class.java)
                    intent.putExtra("countIndexRes", countIndexRes)
                    intent.putExtra("resId", resId)
                    startActivity(intent)
                } else if (restaurantResultCount > 1 && hotelResultCount == 0) {
                    Log.e("placeCount", "more than one restaurant")
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_choosingWhichPlaceFragment)
                } else if (restaurantResultCount == 0 && hotelResultCount == 1) {
                    Log.e("oneHotel", "oneHotel")
                    val countIndexHotel = "0"
                    val hotelId = hotelResponse[0].id
                    val intent = Intent(requireContext(), InsightsActivity::class.java)
                    intent.putExtra("countIndexHotel", countIndexHotel)
                    intent.putExtra("hotelId", hotelId)
                    startActivity(intent)
                } else if (restaurantResultCount == 0 && hotelResultCount > 1) {
                    Log.e("HotelCount", "more than one hotel")
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_choosingWhichPlaceFragment)                }
            }
        }
    }
}