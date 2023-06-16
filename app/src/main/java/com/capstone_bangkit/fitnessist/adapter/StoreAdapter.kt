package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemStoreBinding
import com.capstone_bangkit.fitnessist.model.Store

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.StoreListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val stores = ArrayList<Store>()

    inner class StoreListViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.apply {
                Glide.with(itemView)
                    .load(store.photoUrl)
                    .centerCrop()
                    .into(imageStore)
                tvTitle.text = store.title

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(store)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        val binding =
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        holder.bind(stores[position])
    }

    override fun getItemCount(): Int = stores.size

    fun setStores(listStore: ArrayList<Store>) {
        stores.clear()
        stores.addAll(listStore)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(store: Store)
    }
}
