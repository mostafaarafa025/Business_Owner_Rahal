package com.example.businessowner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.databinding.ReviewItemBinding
import com.example.businessowner.model.getRespond.hotel.ReviewHotel
import java.text.SimpleDateFormat
import java.util.Locale

//import com.example.businessowner.model.neww.ReviewHotelNew
//
class ReviewsHotelAdapter(requestViewModel: RequestViewModel) :RecyclerView.Adapter<ReviewsHotelAdapter.ViewHolder>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd-MM-yyyy / HH:mm", Locale.getDefault())
    inner class ViewHolder(private val viewBinding:ReviewItemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item: ReviewHotel){
            viewBinding.apply {
                humanName.text=item.userId.name
                humanReview.text=item.comment
                timeId.text=item.createdAt
                humanRate.rating=item.rating.toFloat()
                val createdAt = dateFormat.parse(item.createdAt)
                val formattedCreatedAt = outputFormat.format(createdAt)
                timeId.text = formattedCreatedAt

            }
        }
    }
    private val diffUtil=object : DiffUtil.ItemCallback<ReviewHotel>(){
        override fun areItemsTheSame(oldItem: ReviewHotel, newItem: ReviewHotel): Boolean {
            return oldItem.id==newItem.id
        }


        override fun areContentsTheSame(oldItem: ReviewHotel, newItem: ReviewHotel): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsHotelAdapter.ViewHolder {
        return ViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=differ.currentList[position]
        holder.bind(item)
    }
}