package com.devwarex.news.ui.onBoarding

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.devwarex.news.R
import com.devwarex.news.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity(
    R.layout.activity_on_boarding
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<BoardingViewModel>()
        lifecycleScope.launchWhenCreated {
            viewModel.currentStep.collect{
                when(it){
                    2 ->{}
                    3 ->{}
                    4 ->{}
                    else -> Log.d("boarding_step",it.toString())
                }
            }
        }
    }
}