package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.ActivityWorkoutDetailBinding
import com.capstone_bangkit.fitnessist.model.workouts.Exercise
import com.capstone_bangkit.fitnessist.viewmodel.ExerciseViewModel

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDetailBinding
    private lateinit var authentication: AuthenticationManager
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exercise: Exercise
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = AuthenticationManager(this)
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        exercise = intent.getParcelableExtra<Exercise>("exercise")!!

        if(exercise == null)  {
            val intent = Intent(this@WorkoutDetailActivity, WorkoutListActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        binding.tvExerciseName.text = exercise.name.toString()
        val progressBarCountDown = binding.pbTimer
        val textCountDown = binding.tvCountDown
//        val totalTime = (exercise.exerciseLevels!![0].duration!! * 1000).toLong() // convert to miliseconds
        val totalTime = (60 * 1000).toLong() // convert to miliseconds
        textCountDown.text = totalTime.toInt().toString()
        progressBarCountDown.max = totalTime.toInt()

        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished.toFloat() / totalTime * 100).toInt()
                progressBarCountDown.progress = progress
                textCountDown.text = progress.toString()
            }

            override fun onFinish() {
                Toast.makeText(this@WorkoutDetailActivity, "Selesai", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnToggleStart.setOnClickListener {
            var btnText = binding.btnToggleStart.text
            if(btnText == "Mulai") {
                countDownTimer.start()
                binding.btnToggleStart.text = "Pause"
                binding.tvGivExercise.setAnimationFromUrl(exercise.media, "lottie-json-${exercise.id}")
            }
            else {
                binding.btnToggleStart.text = "Mulai"
                binding.tvGivExercise.pauseAnimation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel() // Menghentikan timer saat aktivitas dihancurkan
    }

    companion object {
        const val ARG = "exercise"
    }
}