package com.example.businessowner.Ui.Insights.Signup

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessowner.databinding.ImageViewItemBinding

class PhotoAdapter (private val images: List<Uri>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    class ViewHolder(var viewBinding:ImageViewItemBinding):RecyclerView.ViewHolder(viewBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ImageViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return  images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUri = images[position]
        holder.viewBinding.imageViewId.setImageURI(imageUri)
    }

}