package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private lateinit var selectedGender: String
    private lateinit var selectedActivity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBodyDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        authentication = AuthenticationManager(this)

        val genderOptions = resources.getStringArray(R.array.gender_options)
        val activityOptions = resources.getStringArray(R.array.activity_options)

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.optGender.adapter = genderAdapter

        val activityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activityOptions)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.optActivity.adapter = activityAdapter

        binding.optGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) { selectedGender = genderOptions[position] }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tidak melakukan apa pun ketika tidak ada yang dipilih
            }
        }

        binding.optActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) { selectedActivity = activityOptions[position] }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tidak melakukan apa pun ketika tidak ada yang dipilih
            }
        }

        binding.btnNext.setOnClickListener {
            if (validateInputFields()) {
                authentication.apply {
                    var programId = intent.getStringExtra("program_id")
                    val request =
                        TDEECalculationRequest(
                            gender = selectedGender,
                            age = binding.edtAge.text.toString().toInt(),
                            weight = binding.edtWeight.text.toString().toInt(),
                            height = binding.edtHeight.text.toString().toInt(),
                            activity = selectedActivity,
                            fat = binding.edtFat.text.toString().toDouble(),
                            program_id = programId!!
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
    private fun validateInputFields(): Boolean {
        with(binding) {
            val gender = selectedGender
            val age = edtAge.text.toString()
            val weight = edtWeight.text.toString()
            val height = edtHeight.text.toString()
            val activity = selectedActivity
            val fat = edtFat.text.toString()

            if (gender.isEmpty() || age.isEmpty() || weight.isEmpty() || height.isEmpty() || activity.isEmpty() || fat.isEmpty()) {
                Toast.makeText(this@UserBodyDataActivity, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }
}