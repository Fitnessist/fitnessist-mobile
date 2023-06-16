package com.capstone_bangkit.fitnessist.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.databinding.ItemWorkoutdetailBinding
import com.capstone_bangkit.fitnessist.databinding.ItemWorkoutharianBinding
import com.capstone_bangkit.fitnessist.model.ExerciseProgress
import com.capstone_bangkit.fitnessist.model.workouts.Exercise
import com.capstone_bangkit.fitnessist.model.workouts.Workout

class ExerciseAdapter: RecyclerView.Adapter<ExerciseAdapter.ExerciseListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val exercises = ArrayList<Exercise>()
    private val exerciseProgress = ArrayList<ExerciseProgress>()

    inner class ExerciseListViewHolder(public val binding: ItemWorkoutdetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.apply {
                tvJudulExercise.text = exercise.name
                tvDuration.text = "${exercise.exerciseLevels?.get(0)?.duration.toString()} sec"
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
        val exerciseLevelIdToCheck = exercises[position].exerciseLevels?.get(0)!!.id!!

        val hasMatchingExerciseLevelId = exerciseProgress.any { exerciseProgres ->
                exerciseProgres.exerciseLevelId == exerciseLevelIdToCheck
        }

        holder.bind(exercises[position])
        if (hasMatchingExerciseLevelId){
            holder.binding.cvItemExercise.background = ContextCompat.getDrawable(holder.itemView.context, R.color.greenShapeColor)
        }
    }

    override  fun getItemCount(): Int {
        return exercises.size
    }

    fun setExercises(listExercise: ArrayList<Exercise>) {
        exercises.clear()
        exercises.addAll(listExercise)
        notifyDataSetChanged()
    }

    fun setExerciseProgress(listExerciseProgress: ArrayList<ExerciseProgress>) {
        exerciseProgress.clear()
        exerciseProgress.addAll(listExerciseProgress)

        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(exercise: Exercise)
    }
}