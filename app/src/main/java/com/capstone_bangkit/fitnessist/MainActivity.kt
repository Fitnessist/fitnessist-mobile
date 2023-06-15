package com.capstone_bangkit.fitnessist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.capstone_bangkit.fitnessist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)

        val snapFoodSuccess = intent.getIntExtra("SnapFoodSuccess", -1)
        if (snapFoodSuccess != -1) {
            navController.navigate(snapFoodSuccess)
        }

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}