package com.example.businessowner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.databinding.ReviewItemBinding
import com.example.businessowner.model.getRespond.restaurant.ReviewRes
import java.text.SimpleDateFormat
import java.util.Locale

class ReviewsRestaurantAdapter(requestViewModel: RequestViewModel) :RecyclerView.Adapter<ReviewsRestaurantAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd-MM-yyyy / HH:mm", Locale.getDefault())
        inner class ViewHolder(private val viewBinding:ReviewItemBinding):RecyclerView.ViewHolder(viewBinding.root){

            fun bind(item: ReviewRes){
                viewBinding.apply {
                    humanName.text=item.userId.name
                    humanReview.text=item.comment
                    humanRate.rating=item.rating.toFloat()
                    val createdAt = dateFormat.parse(item.createdAt)
                    val formattedCreatedAt = outputFormat.format(createdAt)
                    timeId.text = formattedCreatedAt
                }
            }
        }
    private val diffUtil=object : DiffUtil.ItemCallback<ReviewRes>(){
        override fun areItemsTheSame(oldItem: ReviewRes, newItem: ReviewRes): Boolean {
            return oldItem.id==newItem.id
        }


        override fun areContentsTheSame(oldItem: ReviewRes, newItem: ReviewRes): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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