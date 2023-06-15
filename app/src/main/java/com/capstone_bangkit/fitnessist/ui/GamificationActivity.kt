package com.capstone_bangkit.fitnessist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.adapter.GamifikasiAdapter
import com.capstone_bangkit.fitnessist.databinding.ActivityGamificationBinding
import com.capstone_bangkit.fitnessist.model.GamifikasiDummy

class GamificationActivity : AppCompatActivity() {
    private lateinit var GamificationAdapter: GamifikasiAdapter
    private lateinit var binding: ActivityGamificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GamificationAdapter = GamifikasiAdapter()
        GamificationAdapter.getGamifikasis(GamifikasiDummy.listGamifikasi)
        GamificationRecyclerView()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun GamificationRecyclerView() {
        binding.rvGamifikasi.apply {
            layoutManager = LinearLayoutManager(this@GamificationActivity, LinearLayoutManager.VERTICAL, false)
            adapter = GamificationAdapter
        }
    }
}