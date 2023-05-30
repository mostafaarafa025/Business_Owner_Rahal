package com.example.businessowner.Ui.Insights.Signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentSignUp1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp1 : Fragment() {
lateinit var binding: FragmentSignUp1Binding

    val images = mutableListOf<Uri>()
    companion object {
        const val PICK_IMAGE_REQUEST = 101
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignUp1Binding.inflate(inflater,container,false)
return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingButton.setOnClickListener {
        Navigation.findNavController(view).navigate(R.id.signUp2)
        }
        val governments=resources.getStringArray(R.array.Government)
        val arrayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,governments)
        binding.governmentSpinnerAutoComplete.setAdapter(arrayAdapter)

        binding.addImageId.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images.add(imageUri)
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!
                images.add(imageUri)
            }
        }
       binding.imageRecyclerView.adapter = PhotoAdapter(images)
        if (images.isNotEmpty()){
            binding.exImage1.visibility=View.GONE
            binding.exImage2.visibility=View.GONE
        }
    }

//    val ADD_PHOTO_REQUEST_CODE = 101
//
//    val photoList = mutableListOf<Photo>()
//    val photoRecyclerView = findViewById<RecyclerView>(R.id.photoRecyclerView)
//    val addPhotoImageView = findViewById<ImageView>(R.id.addPhotoImageView)
//
//    addPhotoImageView.setOnClickListener {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, ADD_PHOTO_REQUEST_CODE)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == ADD_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri = data.data
//            photoList.add(Photo(selectedImageUri))
//            photoRecyclerView.adapter?.notifyItemInserted(photoList.size - 1)
//        }
//    }

}