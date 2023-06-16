package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.ActivityExerciseGoalsBinding
import com.capstone_bangkit.fitnessist.viewmodel.ProgramViewModel

class ExerciseGoalsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseGoalsBinding
    private lateinit var authenticationManager: AuthenticationManager
    private lateinit var programViewModel: ProgramViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationManager = AuthenticationManager(this)
        programViewModel = ViewModelProvider(this)[ProgramViewModel::class.java]
        val accessToken = authenticationManager.getAccess(AuthenticationManager.TOKEN).toString()

        var programId = ""
        binding.btnMenambahBeratBadan.setOnClickListener {
            programId = "a58ef21c-922c-4a43-8d03-5ab6fe06d3e8"
            showLoading(true)
            programViewModel.addUserProgram(accessToken, programId, onSuccess = {
                myProgram ->
                showLoading(false)
                val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java).also {
                    authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                    it.putExtra(UserBodyDataActivity.PROGRAM_ID, programId)
                }
                authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                startActivity(intent)
            },
                onError = {
                    showLoading(false)
                    val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java)
                    startActivity(intent)
                }
            )

        }
        binding.btnMengurangiBeratBadan.setOnClickListener {
            programId = "f6c26ad5-3e7a-4dd8-9e8e-ff40cf24649f"
            showLoading(true)
            programViewModel.addUserProgram(accessToken, programId, onSuccess = {
                    myProgram ->
                showLoading(false)
                val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java).also {
                    authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                    it.putExtra(UserBodyDataActivity.PROGRAM_ID, programId)
                }
                authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                startActivity(intent)
            },
                onError = {
                    showLoading(false)
                    val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java)
                    startActivity(intent)
                }
            )
        }
        binding.btnJagaMasaOtot.setOnClickListener {
            programId = "64c7d2dd-e2dd-4608-b3e4-aed91cd17f6a"
            showLoading(true)
            programViewModel.addUserProgram(accessToken, programId, onSuccess = {
                    myProgram ->
                showLoading(false)
                val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java).also {
                    authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                    it.putExtra(UserBodyDataActivity.PROGRAM_ID, programId)
                }
                authenticationManager.login(AuthenticationManager.PROGRAM_ID, programId)
                startActivity(intent)
            },
                onError = {
                    showLoading(false)
                    val intent = Intent(this@ExerciseGoalsActivity, UserBodyDataActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}