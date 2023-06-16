package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemFoodCardBinding
import com.capstone_bangkit.fitnessist.model.GetFoodHistory

class FoodHistoryAdapter(private val foodHistoryList: MutableList<GetFoodHistory>) : RecyclerView.Adapter<FoodHistoryAdapter.FoodHistoryViewHolder>() {

    inner class FoodHistoryViewHolder(private val binding: ItemFoodCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodHistory: GetFoodHistory) {
            binding.apply {
                Glide.with(itemView)
                    .load(foodHistory.image_url)
                    .centerCrop()
                    .into(imgFoodPicture)
                tvFoodName.text = foodHistory.food_name
                tvFoodCalories.text = foodHistory.total_calories.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodCardBinding.inflate(inflater, parent, false)
        return FoodHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHistoryViewHolder, position: Int) {
        val foodHistory = foodHistoryList[position]
        holder.bind(foodHistory)
    }

    override fun getItemCount(): Int = foodHistoryList.size

    fun setData(listFood: List<GetFoodHistory>) {
        foodHistoryList.clear()
        foodHistoryList.addAll(listFood)
        notifyDataSetChanged()
    }
}
