package com.devwarex.news.ui.home.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devwarex.news.R
import com.devwarex.news.adapters.ArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreen : Fragment(
    R.layout.screen_main
), ArticlesAdapter.ArticleListener{

    private val viewModel by viewModels<MainViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.articles_rv)
        view.findViewById<ImageView>(R.id.main_search_iv).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigate_to_search)
        }
        val adapter = ArticlesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.articles.collect{
                    adapter.setArticles(it)
                }
            }
        }
    }

    override fun onArticleClick(articleUrl: String) {
        val i = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(articleUrl)
        }
        try {
            startActivity(i)
        }catch (e: ActivityNotFoundException){
            Log.e("web_view",e.message!!)
        }
    }

    override fun bookmarkArticle(articleUrl: String, isBooked: Boolean) {
        viewModel.updateArticle(
            url = articleUrl,
            isBooked = isBooked
        )
    }
}