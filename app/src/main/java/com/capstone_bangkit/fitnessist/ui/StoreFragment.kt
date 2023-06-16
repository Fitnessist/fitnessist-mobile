package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone_bangkit.fitnessist.adapter.StoreAdapter
import com.capstone_bangkit.fitnessist.databinding.FragmentStoreBinding
import com.capstone_bangkit.fitnessist.model.Store
import com.capstone_bangkit.fitnessist.model.StoreDummy

class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var storeAdapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        storeAdapter = StoreAdapter()
        storeAdapter.setStores(StoreDummy.listStore)
        storeAdapter.setOnItemClickCallback(object : StoreAdapter.OnItemClickCallback {
            override fun onItemClicked(store: Store) {
                val buy = Intent(requireContext(), SuccessPaymentActivity::class.java)
                startActivity(buy)
            }
        })
        storeRecyclerView()
        return binding.root
    }

    private fun storeRecyclerView() {
        binding.rvStore.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = storeAdapter
        }
    }
}
