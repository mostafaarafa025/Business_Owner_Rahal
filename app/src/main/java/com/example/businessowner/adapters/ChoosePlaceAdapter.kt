package com.example.businessowner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.databinding.ChoosenplaceitemBinding
import com.example.businessowner.model.getRespond.restaurant.Restaurant

class ChoosePlaceAdapter(private val requestViewModel: RequestViewModel):RecyclerView.Adapter<ChoosePlaceAdapter.ViewHolder>() {
        inner class ViewHolder(private val viewBinding:ChoosenplaceitemBinding):RecyclerView.ViewHolder(viewBinding.root){
                fun bind(item:Restaurant,position: Int){
                        itemView.setOnClickListener {

                onItemClickListener?.invoke(item,position)
                        }
                        viewBinding.apply {
                Glide.with(itemView)
                        .load(item.image)
                        .error(R.drawable.restaurant)
                        .into(imageView)
                       textView.text=item.name
                        }
                }
        }
        private val diffUtil=object : DiffUtil.ItemCallback<Restaurant>(){
                override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                        return oldItem.id==newItem.id
                }

                override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                        return oldItem==newItem
                }
        }
        val differ= AsyncListDiffer(this,diffUtil)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                        ChoosenplaceitemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent, false
                        )
                )
        }
        var onItemClickListener: ((Restaurant,Int) -> Unit)? = null
        override fun getItemCount(): Int {
                return  differ.currentList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item=differ.currentList[position]
                holder.bind(item,position)

        }
}