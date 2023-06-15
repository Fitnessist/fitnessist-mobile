package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        authentication = AuthenticationManager(requireContext())

        val getCalories = authentication.getAccessInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET).toString()
        val getName = authentication.getAccess(AuthenticationManager.NAME)
        val getEmail = authentication.getAccess(AuthenticationManager.EMAIL)
        binding.apply {
            tvKebutuhanKalori.text = getCalories
            tvName.text = getName
            tvEmail.text = getEmail
        }

        binding.btnGamification.setOnClickListener {
            val gamificationIntent = Intent(requireContext(), GamificationActivity::class.java)
            startActivity(gamificationIntent)
        }

        binding.btnLogout.setOnClickListener {
            authentication.logOut()
            val logout = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logout)
            requireActivity().finishAffinity()
        }

        return binding.root
    }
}