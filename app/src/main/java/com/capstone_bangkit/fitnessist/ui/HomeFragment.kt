package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.adapter.ArticleHomeAdapter
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentHomeBinding
import com.capstone_bangkit.fitnessist.model.Article
import com.capstone_bangkit.fitnessist.model.ArticleDataDummy
import com.capstone_bangkit.fitnessist.viewmodel.HomeViewModel
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var articleAdapter: ArticleHomeAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleAdapter = ArticleHomeAdapter()
        authentication = AuthenticationManager(requireContext())
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        articleAdapter.getArticles(ArticleDataDummy.listArticle)
        articleRecyclerView()
        onArticleClick()
        binding.tvViewAll.setOnClickListener {
            val article = Intent(context, ArticleActivity::class.java)
            startActivity(article)
        }

        val getName = authentication.getAccess(AuthenticationManager.NAME).toString()
        val programID = authentication.getAccess(AuthenticationManager.PROGRAM_ID).toString()
        binding.tvName.text = getName

        binding.cvChangeData.setOnClickListener {
            val changeData = Intent(requireContext(), ExerciseGoalsActivity::class.java)
            startActivity(changeData)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Memperbarui UI Fragment berdasarkan data yang diperoleh dari ViewModel
        val token = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val programId = authentication.getAccess(AuthenticationManager.PROGRAM_ID).toString()
        Toast.makeText(requireContext(), programId, Toast.LENGTH_SHORT).show()

        homeViewModel.myProgramData.observe(viewLifecycleOwner, Observer { myProgram ->
            authentication.login("PROGRAM", Gson().toJson(myProgram))
            val workoutFragment = WorkoutFragment()
            val tvMyProgram = view.findViewById<TextView>(R.id.my_program_id)
            val pbMyProgram = view.findViewById<ProgressBar>(R.id.my_program_progress_bar)
            val tvPercentage = view.findViewById<TextView>(R.id.tv_my_program_percentage)
            val ivMyProgram = view.findViewById<ImageView>(R.id.iv_myWorkOut)

            ivMyProgram.setOnClickListener {
                val navHostFragment = requireActivity().supportFragmentManager
                    .findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
                val navController = navHostFragment.navController

                val bundle = bundleOf("myProgram" to myProgram)
                navController.navigate(R.id.workoutFragment, bundle)
            }

            tvMyProgram.text = myProgram!!.program?.title
            val percentage = myProgram!!.progressPercent!! * 100

            pbMyProgram.setProgress(percentage, true)
            pbMyProgram.setProgress(percentage, true)
            tvPercentage.text = "$percentage%"
        })

        this.homeViewModel.getMyProgramData(token, programId)
    }

    private fun articleRecyclerView() {
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }
    }

    private fun onArticleClick() {
        articleAdapter.setOnItemClickCallback(object: ArticleHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                Intent(context, DetailArticleActivity::class.java).also {
                    it.putExtra(DetailArticleActivity.PHOTO_URL, article.photoUrl)
                    it.putExtra(DetailArticleActivity.TITLE, article.title)
                    it.putExtra(DetailArticleActivity.DESCRIPTION, article.description)
                    startActivity(it)
                }
            }
        })
    }

}