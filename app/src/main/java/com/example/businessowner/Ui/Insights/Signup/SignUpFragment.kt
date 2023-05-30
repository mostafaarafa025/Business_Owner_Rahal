package com.example.businessowner.Ui.Insights.Signup

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.viewmodel.AuthViewModel
import com.example.businessowner.databinding.FragmentSignUpBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var validEmailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var verifyPasswordEditText: EditText
    private lateinit var validEmailErrorMessage: TextView
    private lateinit var phoneNumberErrorMessage: TextView
    private lateinit var passwordErrorMessage: TextView
    private lateinit var verifyPasswordErrorMessage: TextView
    private lateinit var floatingButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        initializeVariables()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateEmail()
        validatePassword()
        validateVerifyPassword()
        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }

        binding.logInTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }

        floatingButton.setOnClickListener {
    signUp()
        }

    }

    private fun signUp(){
        val name = "tata"
        val role= "user"
        val email= validEmailEditText.text.toString()
        val password=passwordEditText.text.toString()
        val passwordConfirm=verifyPasswordEditText.text.toString()
        authViewModel.signUp(name,email, password, passwordConfirm, role)

        authViewModel.signUpResponse.observe(viewLifecycleOwner, { signUpResponse ->
            signUpResponse?.let {
                Log.d("SignUpResponse", it.toString())
              Log.e("tata",it.token.toString())
            }
        })
    }

//
//    private fun signUp() {
//        val role = "user"
//        authViewModel.signUpResponse.observe(viewLifecycleOwner) {
//
//            val request = SignUpRequest(
//                validEmailEditText.text.toString().trim(),
//                passwordEditText.text.toString().trim(),
//                verifyPasswordEditText.text.toString().trim(),
//                "user")
//
//            authViewModel.signUP(request)
//            Log.e("okay",request.email.toString())
////           request.email = validEmailEditText.text.toString().trim()
////
////            request.password = passwordEditText.text.toString().trim()
//
//
//        }
//    }


    private fun initializeVariables() {
        validEmailEditText = binding.validEmailEditText
        validEmailErrorMessage = binding.errorMessageValidEmail

        phoneNumberEditText = binding.phoneNumberEditText
        phoneNumberErrorMessage = binding.errorMessagePhoneNumber

        passwordEditText = binding.passwordEditText
        passwordErrorMessage = binding.errorMessagePassword

        verifyPasswordEditText = binding.verifyPasswordEditText
        verifyPasswordErrorMessage = binding.errorMessagePasswordVerify

        floatingButton = binding.floatingButton
    }


    private fun showErrorMessage(editText: EditText, textView: TextView, errorMessage: String) {
        editText.error = errorMessage
        textView.text = errorMessage
    }


    private fun validateEmail() {
        val errorMessage = "Enter a valid email"

        validEmailEditText.doAfterTextChanged {
            if (validEmailEditText.text.toString().isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(validEmailEditText.text.toString()).matches()
            ) {
                validEmailErrorMessage.text = ""
            } else {
                showErrorMessage(validEmailEditText, validEmailErrorMessage, errorMessage)
            }
        }
    }

    private fun validatePassword() {
        val errorMessage = "password should contain a-z , A-z , 0-9"
        val uppercase: Pattern = Pattern.compile("[A-Z]")
        val lowercase: Pattern = Pattern.compile("[a-z]")
        val digit: Pattern = Pattern.compile("[0-9]")

        passwordEditText.doAfterTextChanged {
            // if lowercase character is not present
            if (!lowercase.matcher(passwordEditText.text.toString()).find()) {
                showErrorMessage(passwordEditText, passwordErrorMessage, errorMessage)
            } else {
                passwordErrorMessage.text = ""
            }

            // if uppercase character is not present
            if (!uppercase.matcher(passwordEditText.text.toString()).find()) {
                showErrorMessage(passwordEditText, passwordErrorMessage, errorMessage)
            } else {
                passwordErrorMessage.text = ""
            }

            // if digit is not present
            if (!digit.matcher(passwordEditText.text.toString()).find()) {
                showErrorMessage(passwordEditText, passwordErrorMessage, errorMessage)
            } else {
                passwordErrorMessage.text = ""
            }

            // if password length is less than 8
            if (passwordEditText.text.toString().length < 8) {
                showErrorMessage(passwordEditText, passwordErrorMessage, errorMessage)
            } else {
                passwordErrorMessage.text = ""
            }
        }
    }

    private fun validateVerifyPassword() {
        val errorMessage = "Rematch password"
        verifyPasswordEditText.doAfterTextChanged {
            if (passwordEditText.text.toString() == verifyPasswordEditText.text.toString()) {
                verifyPasswordErrorMessage.text = ""
            } else {
                showErrorMessage(verifyPasswordEditText, verifyPasswordErrorMessage, errorMessage)
            }
        }
    }

}