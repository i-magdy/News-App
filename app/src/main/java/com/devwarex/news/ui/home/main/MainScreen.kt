package com.devwarex.news.ui.home.main

import android.os.Bundle
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
        view.findViewById<ImageView>(R.id.main_setting_iv).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigate_to_settings)
        }
        val adapter = ArticlesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                launch {
                    viewModel.articles.collect{
                        adapter.setArticles(it)
                    }
                }
                launch {
                    viewModel.categoriesCount.collect{ count ->
                        if (count < 3){
                            Navigation.findNavController(view).navigate(R.id.action_navigate_to_settings)
                        }
                    }
                }
            }
        }
    }

    override fun onArticleClick(articleUrl: String) {
        /*val i = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(articleUrl)
        }
        try {
            startActivity(i)
        }catch (e: ActivityNotFoundException){
            Log.e("web_view",e.message!!)
        }*/
        val action = MainScreenDirections.actionOpenWebView().apply {
            url = articleUrl
        }
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun bookmarkArticle(articleUrl: String, isBooked: Boolean) {
        viewModel.updateArticle(
            url = articleUrl,
            isBooked = isBooked
        )
    }
}