package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.adapter.AllArticleAdapter
import com.capstone_bangkit.fitnessist.databinding.ActivityArticleBinding
import com.capstone_bangkit.fitnessist.model.Article
import com.capstone_bangkit.fitnessist.model.ArticleDataDummy

class ArticleActivity : AppCompatActivity() {
    private lateinit var articleAdapter: AllArticleAdapter
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleAdapter = AllArticleAdapter()
        articleAdapter.getArticles(ArticleDataDummy.listArticle)
        articleRecyclerView()
        onArticleClick()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun articleRecyclerView() {
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(this@ArticleActivity, LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }
    }

    private fun onArticleClick() {
        articleAdapter.setOnItemClickCallback(object: AllArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                Intent(this@ArticleActivity, DetailArticleActivity::class.java).also {
                    it.putExtra(DetailArticleActivity.PHOTO_URL, article.photoUrl)
                    it.putExtra(DetailArticleActivity.TITLE, article.title)
                    it.putExtra(DetailArticleActivity.DESCRIPTION, article.description)
                    startActivity(it)
                }
            }
        })
    }
}