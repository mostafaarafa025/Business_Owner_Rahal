package com.example.businessowner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.databinding.ReviewItemBinding
import com.example.businessowner.model.Respond.Restaurant.Review

class ReviewsAdapter(requestViewModel: RequestViewModel) :RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
        inner class ViewHolder(private val viewBinding:ReviewItemBinding):RecyclerView.ViewHolder(viewBinding.root){

            fun bind(item:Review){
                viewBinding.apply {
                    humanName.text=item.name
                    humanReview.text=item.comment
                    humanRate.rating=item.rating.toFloat()

                }
            }
        }
    private val diffUtil=object : DiffUtil.ItemCallback<Review>(){
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id==newItem.id
        }


        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
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