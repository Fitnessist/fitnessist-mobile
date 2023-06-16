package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemGamifikasiBinding
import com.capstone_bangkit.fitnessist.model.Gamifikasi

class GamifikasiAdapter: RecyclerView.Adapter<GamifikasiAdapter.GamifikasiListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val Gamifikasi = ArrayList<Gamifikasi>()
    inner class GamifikasiListViewHolder(private val binding: ItemGamifikasiBinding): RecyclerView.ViewHolder(binding.root) {
        fun getGamifikasi(Gamifikasi: Gamifikasi) {
            binding.apply {
                Glide.with(itemView)
                    .load(Gamifikasi.photoUrl)
                    .centerCrop()
                    .into(imgGamifikasiThumbnail)
                tvGamifikasiPoint.text = Gamifikasi.point
                tvGamificationTitle.text = Gamifikasi.title

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(Gamifikasi)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamifikasiAdapter.GamifikasiListViewHolder {
        val data = ItemGamifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamifikasiListViewHolder(data)
    }

    override fun onBindViewHolder(holder: GamifikasiAdapter.GamifikasiListViewHolder, position: Int) {
        holder.getGamifikasi(Gamifikasi[position])
    }

    override  fun getItemCount(): Int = 6

    fun getGamifikasis(listGamifikasi: ArrayList<Gamifikasi>) {
        Gamifikasi.clear()
        Gamifikasi.addAll(listGamifikasi)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(Gamifikasi: Gamifikasi)
    }
}