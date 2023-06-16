package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone_bangkit.fitnessist.databinding.ItemWorkoutdetailBinding
import com.capstone_bangkit.fitnessist.model.workouts.Exercise

class ExerciseAdapter: RecyclerView.Adapter<ExerciseAdapter.ExerciseListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val exercises = ArrayList<Exercise>()

    inner class ExerciseListViewHolder(private val binding: ItemWorkoutdetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.apply {
                tvJudulExercise.text = exercise.name
                tvDuration.text = "${exercise.exerciseLevels?.get(0)?.duration.toString()} min"
                tvTotalCalory.text = "${exercise.exerciseLevels?.get(0)?.caloriesBurned.toString()} cal"
                tvPointWillEarned.text = exercise.exerciseLevels?.get(0)?.points.toString()

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(exercise)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAdapter.ExerciseListViewHolder {
        val data = ItemWorkoutdetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseListViewHolder(data)
    }

    override fun onBindViewHolder(holder: ExerciseAdapter.ExerciseListViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override  fun getItemCount(): Int {
        return exercises.size
    }

    fun setExercises(listExercise: ArrayList<Exercise>) {
        exercises.clear()
        exercises.addAll(listExercise)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(exercise: Exercise)
    }
}