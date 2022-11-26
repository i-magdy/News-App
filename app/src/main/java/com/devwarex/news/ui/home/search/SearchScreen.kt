package com.devwarex.news.ui.home.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
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
class SearchScreen : Fragment(
    R.layout.screen_search
), ArticlesAdapter.ArticleListener {

    val viewModel by viewModels<SearchViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArticlesAdapter(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.articles_rv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotBlank()) {
                    viewModel.search(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.articles.collect{adapter.setArticles(it)}
            }
        }
    }

    override fun onArticleClick(articleUrl: String) {
        val action = SearchScreenDirections.actionOpenWebViewFromSearch().apply {
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