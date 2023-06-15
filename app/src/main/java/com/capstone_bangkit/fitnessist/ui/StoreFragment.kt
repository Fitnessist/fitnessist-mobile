package com.capstone_bangkit.fitnessist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone_bangkit.fitnessist.adapter.StoreAdapter
import com.capstone_bangkit.fitnessist.databinding.FragmentStoreBinding
import com.capstone_bangkit.fitnessist.model.Store

class StoreFragment : Fragment(), StoreAdapter.OnItemClickCallback {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var storeAdapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        storeAdapter = StoreAdapter()
        storeRecyclerView()
        return binding.root
    }

    private fun storeRecyclerView() {
        binding.rvStore.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = storeAdapter
        }
    }

    override fun onItemClicked(store: Store) {
        // Implement your action when a store item is clicked
    }
}
