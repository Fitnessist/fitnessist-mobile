package com.capstone_bangkit.fitnessist.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.ActivityWorkoutDetailBinding
import com.capstone_bangkit.fitnessist.model.ExerciseProgress
import com.capstone_bangkit.fitnessist.model.workouts.Exercise
import com.capstone_bangkit.fitnessist.ui.countdown.CustomCountdownTimer
import com.capstone_bangkit.fitnessist.viewmodel.ExerciseViewModel

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDetailBinding
    private lateinit var authentication: AuthenticationManager
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exercise: Exercise
    private lateinit var popupDialog: Dialog
    private lateinit var programId: String
    private lateinit var customCountdownTimer: CustomCountdownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        authentication = AuthenticationManager(this)
        programId = authentication.getAccess(AuthenticationManager.PROGRAM_ID).toString()
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
        val ivLottie = binding.tvGivExercise
        ivLottie.setAnimationFromUrl(exercise.media, "lottie-json-${exercise.media}")
        createDialog()

        val totalTime = (exercise.exerciseLevels!![0].duration!!).toLong() // convert to miliseconds
//        val totalTime = (60).toLong() // 1 menit
        val totalTimeInMili = (totalTime * 1_000)// convert to miliseconds

        textCountDown.text = totalTime.toInt().toString()

        customCountdownTimer = CustomCountdownTimer(totalTimeInMili, 1_000)
        customCountdownTimer.onTick = { millisUntilFinished ->
            val progress = (millisUntilFinished.toFloat() / totalTimeInMili * totalTime).toInt()
            progressBarCountDown.progress = progress
            textCountDown.text = progress.toString()
        }

        customCountdownTimer.onFinish = {
            if( binding.btnToggleStart.text.contentEquals("Pause", true)){
                binding.btnToggleStart.text = "Selesai"
            }
            if(ivLottie.isAnimating){
                ivLottie.pauseAnimation()
            }
            postExerciseProgress(exercise)
            showCompletedPopup(exercise)
        }

        progressBarCountDown.max = totalTime.toInt()

        binding.btnToggleStart.setOnClickListener {
            var btnText = binding.btnToggleStart.text
            if(btnText.contentEquals("mulai", true)) {
                customCountdownTimer.startTimer()
                binding.btnToggleStart.text = "Pause"
                ivLottie.playAnimation()
            }
            else if (btnText.contentEquals("pause", true)) {
                binding.btnToggleStart.text = "Resume"
                customCountdownTimer.pauseTimer()
                ivLottie.pauseAnimation()
            }
            else if (btnText.contentEquals("resume", true)) {
                binding.btnToggleStart.text = "Pause"
                customCountdownTimer.resumeTimer()
                ivLottie.resumeAnimation()
            }
            else if (btnText.contentEquals("selesai", true)) {
                binding.btnToggleStart.text = "Mulai"
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }

    }

    private fun createDialog() {
        popupDialog = Dialog(this)
        popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popupDialog.setContentView(R.layout.fragment_workout_pop_up)
        popupDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupDialog.setCancelable(false)


    }

    private fun onBackPressedMethod() {
        customCountdownTimer.destroyTimer()
        finish()
    }

    private fun showPopup(data: Exercise) {
        val btnClosePopup = popupDialog.findViewById<Button>(R.id.btnClose)
        val tvEarnedPoints = popupDialog.findViewById<TextView>(R.id.tv_earned_points)
        tvEarnedPoints.text = data.exerciseLevels!![0].points.toString()

        btnClosePopup.setOnClickListener {
            popupDialog.dismiss()
        }
        popupDialog.show()
    }

    private fun postExerciseProgress(data: Exercise){
        val token = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val request = ExerciseProgress(programId = programId, exerciseId = data.id, exerciseLevelId = data.exerciseLevels!!.get(0).id!!, workoutId = data.workoutId, exercise = null, exerciseLevels = null, workout = null, program = null, user = null)

        exerciseViewModel.postExerciseProgress(token, request, onSuccess = {
                Toast.makeText(this@WorkoutDetailActivity, "berhasil menambahkan data :" + it, Toast.LENGTH_SHORT).show()
            },
            onError = {
                Toast.makeText(this@WorkoutDetailActivity, it, Toast.LENGTH_SHORT).show()
            })

    }

    private fun showCompletedPopup(data: Exercise) {
        showPopup(data)
    }

    override fun onDestroy() {
        customCountdownTimer.destroyTimer()
        super.onDestroy()
    }

    companion object {
        const val ARG = "exercise"
    }
}