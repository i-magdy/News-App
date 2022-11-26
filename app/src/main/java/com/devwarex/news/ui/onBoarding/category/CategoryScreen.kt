package com.devwarex.news.ui.onBoarding.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.devwarex.news.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryScreen: Fragment(
    R.layout.screen_category
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("cate","create")
    }
}