package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.MainActivity
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.api.TDEECalculationRequest
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.authentication.AuthenticationViewModel
import com.capstone_bangkit.fitnessist.databinding.ActivityUserBodyDataBinding

class UserBodyDataActivity : AppCompatActivity() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var binding: ActivityUserBodyDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBodyDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        authentication = AuthenticationManager(this)

        binding.btnNext.setOnClickListener {
            if (binding.edtGender.text.isNotEmpty() && binding.edtAge.text.isNotEmpty() && binding.edtWeight.text.isNotEmpty() && binding.edtHeight.text.isNotEmpty() && binding.edtActivity.text.isNotEmpty() && binding.edtFat.text.isNotEmpty()) {
                authentication.apply {

                    val request =
                        TDEECalculationRequest(
                            gender = binding.edtGender.text.toString(),
                            age = binding.edtAge.text.toString().toInt(),
                            weight = binding.edtWeight.text.toString().toInt(),
                            height = binding.edtHeight.text.toString().toInt(),
                            activity = binding.edtActivity.text.toString(),
                            fat = binding.edtFat.text.toString().toDouble(),
                            program_id = "f6c26ad5-3e7a-4dd8-9e8e-ff40cf24649f"
                        )

                    val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
                    val tokenAccess = "Bearer $getToken"
                    if (request != null) {
                        authenticationViewModel.addTDEECalculation(tokenAccess, request,
                            onSuccess = { response ->
                                login(AuthenticationManager.GENDER, response.data.gender)
                                loginInt(AuthenticationManager.AGE, response.data.age)
                                loginInt(AuthenticationManager.WEIGHT, response.data.weight)
                                loginInt(AuthenticationManager.HEIGHT, response.data.height)
                                login(AuthenticationManager.ACTIVITY, response.data.activity)
                                loginInt(AuthenticationManager.FAT, response.data.fat)
                                loginInt(AuthenticationManager.CALORIES_EACH_DAY, response.data.calories_each_day)
                                loginInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET, response.data.calories_each_day_target)

                                val login = Intent(this@UserBodyDataActivity, MainActivity::class.java)
                                startActivity(login)
                                finishAffinity()
                            }, onError = { errorMessage ->
                                Toast.makeText(this@UserBodyDataActivity, "Error memasukkan data", Toast.LENGTH_SHORT).show()
                            })
                    }
                    authenticationViewModel.getTDEECalculation(tokenAccess,
                        onSuccess = { response ->
                            if (response.data.age != 0) {
                                login(AuthenticationManager.NAME, response.data.user.name)
                                login(AuthenticationManager.USERNAME, response.data.user.username)
                                val login = Intent(this@UserBodyDataActivity, MainActivity::class.java)
                                startActivity(login)
                                finishAffinity()
                            }
                        }, onError = { errorMessage ->
                            Toast.makeText(this@UserBodyDataActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        })
                }
                val login = Intent(this@UserBodyDataActivity, MainActivity::class.java)
                startActivity(login)
                finishAffinity()
            }
        }
    }
    companion object {
        const val PROGRAM_ID = "program_id"
    }
}