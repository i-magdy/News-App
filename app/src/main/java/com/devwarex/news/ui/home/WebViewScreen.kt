package com.devwarex.news.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devwarex.news.R

class WebViewScreen : Fragment(
    R.layout.web_view_screen
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args by navArgs<WebViewScreenArgs>()
        try {
            view.findViewById<WebView>(R.id.article_web_view).loadUrl(args.url)
        }catch (e: Exception){
            Log.e("web_view",e.message.toString())
        }
    }
}