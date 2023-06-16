package com.capstone_bangkit.fitnessist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.databinding.FragmentWorkoutPopUpBinding

class WorkoutPopUpFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutPopUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutPopUpBinding.inflate(inflater)

        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
