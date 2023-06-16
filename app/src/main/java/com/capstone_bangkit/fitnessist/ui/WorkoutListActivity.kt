package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.adapter.ExerciseAdapter
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.ActivityWorkoutListBinding
import com.capstone_bangkit.fitnessist.model.workouts.Exercise
import com.capstone_bangkit.fitnessist.model.workouts.Workout
import com.capstone_bangkit.fitnessist.viewmodel.ExerciseViewModel

class WorkoutListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutListBinding
    private lateinit var authentication: AuthenticationManager
    private lateinit var workoutViewModel: ExerciseViewModel
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authentication = AuthenticationManager(this)
        workoutViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        exerciseAdapter = ExerciseAdapter()

        val token = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val workout = intent.getParcelableExtra<Workout>("workout")
        Toast.makeText(this, workout.toString(), Toast.LENGTH_SHORT).show()

        if (workout == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.workoutFragment, WorkoutFragment())
            transaction.addToBackStack(null)
            transaction.commitAllowingStateLoss()
            this.finishAffinity()
        }


        workoutViewModel.workouts.observe(this, Observer {
            exerciseAdapter.setExercises(it?.exercises!!)
            exerciseRecyclerView()
            onExerciseClick()
        })

        this.workoutViewModel.getWorkoutWithId(
            workout!!.id!!,
            onError = {
                Toast.makeText(this@WorkoutListActivity, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun exerciseRecyclerView() {
        binding.rvUpcomingSessionss.apply {
            layoutManager = LinearLayoutManager(this@WorkoutListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = exerciseAdapter
        }
    }

    private fun onExerciseClick() {
        exerciseAdapter.setOnItemClickCallback(object: ExerciseAdapter.OnItemClickCallback {
            override fun onItemClicked(exercise: Exercise) {
                Intent(this@WorkoutListActivity, WorkoutDetailActivity::class.java).also {
                    it.putExtra(WorkoutDetailActivity.ARG, exercise)
                    startActivity(it)
                }
            }
        })
    }

}
