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
import com.example.businessowner.model.getRespond.hotel.Hotel
import com.example.businessowner.model.getRespond.restaurant.Restaurant

class ChooseHotelAdapter(private val requestViewModel: RequestViewModel): RecyclerView.Adapter<ChooseHotelAdapter.ViewHolder>() {
    inner class ViewHolder(private val viewBinding: ChoosenplaceitemBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item:Hotel , position:Int){
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item,position)
            }
            viewBinding.apply {
                Glide.with(itemView)
                    .load(item.image)
                    .error(R.drawable.hotel)
                    .into(imageView)
                textView.text=item.name
            }
        }

    }
    private val diffUtil=object : DiffUtil.ItemCallback<Hotel>(){
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseHotelAdapter.ViewHolder {
        return ViewHolder(
            ChoosenplaceitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    var onItemClickListener: ((Hotel, Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=differ.currentList[position]
        holder.bind(item,position)
    }

}