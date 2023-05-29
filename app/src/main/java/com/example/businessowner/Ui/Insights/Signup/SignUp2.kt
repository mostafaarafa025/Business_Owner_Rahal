package com.example.businessowner.Ui.Insights.Signup

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.format.DateFormat.is24HourFormat
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentSignUp2Binding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale


class SignUp2 : Fragment() {
   lateinit var binding: FragmentSignUp2Binding
    val ADD_PDF_REQUEST_CODE = 102
    private var isChoosingStartTime = true
    private var selectedStartTime: String? = null
    private var selectedTimeIndex = 0
    private val timeOptions = arrayOf("from", "to")
    private var selectedEndTime: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUp2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backArrowButton.setOnClickListener {
Navigation.findNavController(view).navigate(R.id.signUp1)
        }
      binding.submitButton.setOnClickListener {
          Navigation.findNavController(view).navigate(R.id.loadingOwnerFragment)
      }
        binding.iconUpload.setOnClickListener {
            pickPdf()
            putPdf()
        }
        binding.editTextTimeFrom.setOnClickListener {
            openTimePickerFrom()
        }
        binding.editTextTimeTo.setOnClickListener {
            openTimePickerTo()
        }
        val days=resources.getStringArray(R.array.days)
        val arrayAdapter= ArrayAdapter(requireContext(), R.layout.dropdown_item,days)
        binding.daysSpinnerAutoComplete.setAdapter(arrayAdapter)
    }

    private fun openTimePickerFrom() {
      val isSystem24Hour = is24HourFormat(requireContext())
       val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker=MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("from")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
      picker.show(childFragmentManager,"TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

            binding.editTextTimeFrom.setText(selectedTime)
        }
    }
    private fun openTimePickerTo() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker=MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("To")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
        picker.show(childFragmentManager,"TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

            // Update the selected time in the spinner
            binding.editTextTimeTo.setText(selectedTime)
        }
    }
//private fun openTimePicker() {
//    val isSystem24Hour = is24HourFormat(requireContext())
//    val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
//
//    val fromPicker = MaterialTimePicker.Builder()
//        .setTimeFormat(clockFormat)
//        .setHour(12)
//        .setMinute(0)
//        .setTitleText("From")
//        .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
//        .build()
//
//    val toPicker = MaterialTimePicker.Builder()
//        .setTimeFormat(clockFormat)
//        .setHour(12)
//        .setMinute(0)
//        .setTitleText("To")
//        .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
//        .build()
//
//    fromPicker.show(childFragmentManager, "FromPicker")
//    toPicker.show(childFragmentManager, "ToPicker")
//
//    fromPicker.addOnPositiveButtonClickListener {
//        val selectedHour = fromPicker.hour
//        val selectedMinute = fromPicker.minute
//        val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//
//        // Update the selected time in the "From" spinner
//        binding.hoursSpinnerAutoComplete.setText(selectedTime)
//    }
//
//    toPicker.addOnPositiveButtonClickListener {
//        val selectedHour = toPicker.hour
//        val selectedMinute = toPicker.minute
//        val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//
//        // Update the selected time in the "To" spinner
//        binding.daysSpinnerAutoComplete.setText(selectedTime)
//    }
//}
//private fun openTimePicker() {
//    val isSystem24Hour = is24HourFormat(requireContext())
//    val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
//
//    val fromPicker = MaterialTimePicker.Builder()
//        .setTimeFormat(clockFormat)
//        .setHour(12)
//        .setMinute(0)
//        .setTitleText("From")
//        .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
//        .build()
//
//    val toPicker = MaterialTimePicker.Builder()
//        .setTimeFormat(clockFormat)
//        .setHour(12)
//        .setMinute(0)
//        .setTitleText("To")
//        .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
//        .build()
//
//    fromPicker.show(childFragmentManager, "FromPicker")
//    toPicker.show(childFragmentManager, "ToPicker")
//
//    fromPicker.addOnPositiveButtonClickListener {
//        val selectedHour = fromPicker.hour
//        val selectedMinute = fromPicker.minute
//        val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//
//        val fromText = "From"
//        val toText = "To"
//
//        // Create a SpannableString for the selected time with orange color
//        val orangeColor = Color.parseColor("#FFA500")
//        val selectedTimeSpannable = SpannableString(selectedTime)
//        selectedTimeSpannable.setSpan(ForegroundColorSpan(orangeColor), 0, selectedTimeSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        // Create a SpannableStringBuilder for the final text in the spinner
//        val builder = SpannableStringBuilder()
//            .append(fromText).append(" - ").append(selectedTimeSpannable).append(" - ")
//            .append(toText).append(" - ").append(selectedTimeSpannable)
//
//        // Update the text in the spinner
//        binding.hoursSpinnerAutoComplete.setText(builder, TextView.BufferType.SPANNABLE)
//    }
//
//    toPicker.addOnPositiveButtonClickListener {
//        val selectedHour = toPicker.hour
//        val selectedMinute = toPicker.minute
//        val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//
//        val fromText = "From"
//        val toText = "To"
//
//        // Create a SpannableString for the selected time with orange color
//        val orangeColor = Color.parseColor("#FFA500")
//        val selectedTimeSpannable = SpannableString(selectedTime)
//        selectedTimeSpannable.setSpan(ForegroundColorSpan(orangeColor), 0, selectedTimeSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        // Get the existing text from the spinner
//        val existingText = binding.hoursSpinnerAutoComplete.text.toString()
//
//        // Append the selected time to the existing text using SpannableStringBuilder
//        val builder = SpannableStringBuilder(existingText)
//            .append(" - ").append(selectedTimeSpannable)
//
//        // Update the text in the spinner
//        binding.hoursSpinnerAutoComplete.setText(builder, TextView.BufferType.SPANNABLE)
//    }
//}



    private fun pickPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        startActivityForResult(pdfIntent, ADD_PDF_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Handle photo selection as before
            val selectedPdfUri = data.data


      }
        //        else if (requestCode == ADD_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

    }

    private fun putPdf() {
        binding.pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=selectedPdfUri")
        binding.pdfWebView.visibility=View.VISIBLE
        // Do whatever you need to do with the selected PDF file (e.g., upload to a server)
        binding.button.visibility=View.GONE
        binding.iconUpload.visibility=View.GONE
    }
}