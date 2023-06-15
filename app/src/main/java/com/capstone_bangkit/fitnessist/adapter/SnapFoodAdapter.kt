package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemFoodHorizontalBinding
import com.capstone_bangkit.fitnessist.model.Food
import com.capstone_bangkit.fitnessist.model.FoodPrediction

class SnapFoodAdapter(private val foodList: List<FoodPrediction>): RecyclerView.Adapter<SnapFoodAdapter.SnapFoodViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val food = ArrayList<Food>()
    inner class SnapFoodViewHolder(private val binding: ItemFoodHorizontalBinding): RecyclerView.ViewHolder(binding.root) {
        fun getSnapFood(foodPrediction: FoodPrediction) {
            binding.apply {
                Glide.with(itemView)
                    .load(foodPrediction.food?.image_url)
                    .centerCrop()
                    .into(imgFoodPicture)
                tvFoodName.text = foodPrediction.food?.food_name
                tvFoodCalories.text = foodPrediction.food?.calories_per_100gr.toString()
                tvMatchPercentage.text = String.format("%.1f", foodPrediction.confidence_percentage)

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(foodPrediction)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SnapFoodAdapter.SnapFoodViewHolder {
        val data = ItemFoodHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SnapFoodViewHolder(data)
    }

    override fun onBindViewHolder(holder: SnapFoodAdapter.SnapFoodViewHolder, position: Int) {
        val foodPrediction = foodList[position]
        holder.getSnapFood(foodPrediction)
    }

    override  fun getItemCount(): Int = foodList.size

    fun getSnapFoods(listFood: ArrayList<Food>) {
        food.clear()
        food.addAll(listFood)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(foodPrediction: FoodPrediction)
    }
}