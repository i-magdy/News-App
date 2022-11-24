package com.devwarex.news.ui

import android.os.Bundle
import android.util.Log
import com.devwarex.news.R

class MainActivity : BaseActivity(
    R.layout.activity_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("asd","test")
    }
}