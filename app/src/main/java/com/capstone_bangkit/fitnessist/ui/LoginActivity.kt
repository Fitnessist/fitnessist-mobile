package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.MainActivity
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.api.PostTDEECalculationRequest
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.authentication.AuthenticationViewModel
import com.capstone_bangkit.fitnessist.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        supportActionBar?.hide()
        authentication = AuthenticationManager(this)

        binding.btnRegister.setOnClickListener {
            val register = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(register)
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }
    private fun loginUser() {
        showLoading(true)
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        authenticationViewModel.login(email, password,
            onSuccess = { token ->
                authentication.apply {
                    setSession(AuthenticationManager.SESSION, true)
                    login(AuthenticationManager.TOKEN, token)
                    login(AuthenticationManager.EMAIL, email)

                    Toast.makeText(this@LoginActivity, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                    showLoading(false)
                    val login = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(login)
                    finishAffinity()

                    val gender = ""
                    val age = 0
                    val weight = 0
                    val height = 0
                    val activity = ""
                    val fat = 0.0
                    val programId = ""

                    val request = PostTDEECalculationRequest(
                        gender = gender,
                        age = age,
                        weight = weight,
                        height = height,
                        activity = activity,
                        fat = fat,
                        program_id = programId
                    )
                    val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
                    val tokenAccess = "Bearer $getToken"
                    authenticationViewModel.getTDEECalculation(tokenAccess,
                        onSuccess = { response ->
                            if (response.data.age != 0) {
                                login(AuthenticationManager.GENDER, response.data.gender)
                                loginInt(AuthenticationManager.AGE, response.data.age)
                                loginInt(AuthenticationManager.WEIGHT, response.data.weight)
                                loginInt(AuthenticationManager.HEIGHT, response.data.height)
                                login(AuthenticationManager.ACTIVITY, response.data.activity)
                                loginInt(AuthenticationManager.FAT, response.data.fat)
                                loginInt(AuthenticationManager.CALORIES_EACH_DAY, response.data.calories_each_day)
                                loginInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET, response.data.calories_each_day_target)
                                login(AuthenticationManager.NAME, response.data.user.name)
                                login(AuthenticationManager.USERNAME, response.data.user.username)
                                val login = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(login)
                                finishAffinity()
                            }
                        }, onError = { errorMessage ->
                            val login = Intent(this@LoginActivity, ExerciseGoalsActivity::class.java)
                            startActivity(login)
                            finishAffinity()
                        })
                }
            }, onError = { errorMessage ->
                showLoading(false)
                Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()

            })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        if (authentication.checkSession(AuthenticationManager.SESSION) == true) {
            val login = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(login)
            finishAffinity()
        }
    }
}