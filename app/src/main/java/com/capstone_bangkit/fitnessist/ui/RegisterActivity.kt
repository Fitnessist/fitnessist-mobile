package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.authentication.AuthenticationViewModel
import com.capstone_bangkit.fitnessist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        supportActionBar?.hide()
        showLoading(false)

        binding.btnLogin.setOnClickListener {
            val login = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(login)
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }
    private fun registerUser() {
        showLoading(true)
        binding.apply {
            val username = edtUsername.text.toString().trim()
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val passwordConfirmation = edtPassConf.text.toString().trim()

            authenticationViewModel.register(username,name, email, password, passwordConfirmation,
            onSuccess = {
                showLoading(false)
                Toast.makeText(this@RegisterActivity, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
                val login = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(login)
                finishAffinity()
            },
            onError = { errorMessage ->
                showLoading(false)
                Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}