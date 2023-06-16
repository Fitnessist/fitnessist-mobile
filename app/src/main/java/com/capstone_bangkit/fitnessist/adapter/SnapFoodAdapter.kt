package com.capstone_bangkit.fitnessist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemFoodHorizontalBinding
import com.capstone_bangkit.fitnessist.model.Food
import com.capstone_bangkit.fitnessist.model.FoodPrediction
import com.capstone_bangkit.fitnessist.ui.AddFoodActivity

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
                val predictionData = String.format("%.1f", foodPrediction.confidence_percentage)
                tvMatchPercentage.text = "$predictionData%"

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(foodPrediction)

                    val intent = Intent(itemView.context, AddFoodActivity::class.java)
                    intent.putExtra("food_id", foodPrediction.food?.id)
                    intent.putExtra("food_name", foodPrediction.food?.food_name)
                    intent.putExtra("image_url", foodPrediction.food?.image_url)
                    intent.putExtra("calories_per_100gr", foodPrediction.food?.calories_per_100gr.toString())
                    itemView.context.startActivity(intent)
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

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(foodPrediction: FoodPrediction)
    }
}