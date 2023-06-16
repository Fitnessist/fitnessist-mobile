package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.adapter.WorkoutHarianAdapter
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentWorkoutBinding
import com.capstone_bangkit.fitnessist.model.workouts.MyProgram
import com.capstone_bangkit.fitnessist.model.workouts.Workout
import com.capstone_bangkit.fitnessist.viewmodel.WorkoutViewModel
import com.google.gson.Gson

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var authentication: AuthenticationManager
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var workoutAdapter: WorkoutHarianAdapter
    private lateinit var myProgram: MyProgram

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authentication = AuthenticationManager(requireContext())
        workoutAdapter = WorkoutHarianAdapter()
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        val programString = authentication.getAccess("PROGRAM")
        myProgram = Gson().fromJson(programString, MyProgram::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val programId = authentication.getAccess(AuthenticationManager.PROGRAM_ID).toString()

        workoutViewModel.workouts.observe(viewLifecycleOwner, Observer {
            binding.tvProgramTitle.text = myProgram.program!!.title
            binding.dailyCardProgressBar.progress = myProgram.progressPercent!!
            binding.dailyCardProgressBar.max = 100
            binding.tvPercentProgress.text = "${myProgram.progressPercent!!}% about your progress"
            val workoutsArray = it as ArrayList<Workout>

            workoutAdapter.setWorkouts(workoutsArray)
            workoutRecyclerView()
            setWorkoutOnClick()
        })

        this.workoutViewModel.getAllWorkouts(token, programId)
    }

    private fun workoutRecyclerView() {
        binding.rvWorkouts.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = workoutAdapter
        }
    }
    private fun setWorkoutOnClick() {
        workoutAdapter.setOnItemClickCallback(object: WorkoutHarianAdapter.OnItemClickCallback{
            override fun onItemClicked(workout: Workout) {
                Intent(requireActivity(), WorkoutListActivity::class.java).also {
                    it.putExtra("workout", workout)
                    startActivity(it)
                }
            }
        })
    }
}