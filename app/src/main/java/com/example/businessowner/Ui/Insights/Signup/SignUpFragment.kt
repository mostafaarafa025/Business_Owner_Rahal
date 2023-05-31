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
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.viewmodel.AuthViewModel
import com.example.businessowner.databinding.FragmentSignUpBinding
import com.example.businessowner.model.authentication.SignUpRequest
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
    private lateinit var fullNameEditText:EditText
    private lateinit var fullNameErrorMessage:TextView


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
        validateFullName()
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
        val name=fullNameEditText.text.toString()
        val email= validEmailEditText.text.toString()
        val password=passwordEditText.text.toString()
        val passwordConfirm=verifyPasswordEditText.text.toString()
        val role= "business_owner"
        val signUpRequest = SignUpRequest().apply {
            this.name=name
            this.email = email
            this.password = password
            this.passwordConfirm = passwordConfirm
            this.role = role
        }
        authViewModel.signUp(signUpRequest)
        authViewModel.signUpResponse.observe(viewLifecycleOwner){ it->
            if (it!=null){
                it?.let {
                    Log.e("success",it.data.user.name.toString())
                    Log.e("success",it.data.user.email.toString())
                    Log.e("success",it.data.user.password.toString())
                    Log.e("success",it.data.user.role.toString())
                    Log.e("success",it.token.toString())
                }
                Toast.makeText(activity, "Register success", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun initializeVariables() {
        fullNameEditText = binding.fullNameEditText
        fullNameErrorMessage = binding.errorMessageFullName

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

    private fun validateFullName(){
        val errorMessage = "Name is required"

        fullNameEditText.doOnTextChanged { _, _, _, _ ->
            fullNameErrorMessage.text = ""
        }
        fullNameEditText.doAfterTextChanged {
            if (fullNameEditText.text.toString() == ""){
                showErrorMessage(fullNameEditText,fullNameErrorMessage, errorMessage)
            }
        }
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