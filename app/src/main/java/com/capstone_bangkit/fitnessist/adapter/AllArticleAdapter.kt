package com.capstone_bangkit.fitnessist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone_bangkit.fitnessist.databinding.ItemArticleHorizontalBinding
import com.capstone_bangkit.fitnessist.model.Article

class AllArticleAdapter: RecyclerView.Adapter<AllArticleAdapter.ArticleListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val article = ArrayList<Article>()
    inner class ArticleListViewHolder(private val binding: ItemArticleHorizontalBinding): RecyclerView.ViewHolder(binding.root) {
        fun getArticle(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.photoUrl)
                    .centerCrop()
                    .into(imgArticleThumbnail)
                tvArticleTitle.text = article.title
                tvStoriesDescription.text = article.description

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllArticleAdapter.ArticleListViewHolder {
        val data = ItemArticleHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleListViewHolder(data)
    }

    override fun onBindViewHolder(holder: AllArticleAdapter.ArticleListViewHolder, position: Int) {
        holder.getArticle(article[position])
    }

    override  fun getItemCount(): Int = article.size

    fun getArticles(listArticle: ArrayList<Article>) {
        article.clear()
        article.addAll(listArticle)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: Article)
    }
}