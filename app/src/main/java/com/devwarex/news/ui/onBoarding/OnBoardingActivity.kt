package com.devwarex.news.ui.onBoarding

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.devwarex.news.R
import com.devwarex.news.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity(
    R.layout.activity_on_boarding
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<BoardingViewModel>()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                delay(500)
                val nav = Navigation.findNavController(
                    activity = this@OnBoardingActivity,
                    viewId = R.id.boarding_nav_host_fragment
                )
                viewModel.currentStep.collect {
                    when (it) {
                        2 -> {
                            val dest = nav.currentDestination
                            if (dest != null && dest.label == "welcome_screen"){
                                nav.navigate(R.id.action_navigate_to_country_screen)
                            }
                        }
                        3 -> {
                            val dest  = nav.currentDestination
                            if (dest != null && dest.label == "welcome_screen"){
                                nav.navigate(R.id.action_navigate_to_country_screen)
                                nav.navigate(R.id.action_navigate_to_category_screen)
                            }else if (dest?.label == "country_screen"){
                                nav.navigate(R.id.action_navigate_to_category_screen)
                            }
                        }
                        4 -> {}
                        else -> Log.d("boarding_step", it.toString())
                    }
                }
            }
        }
    }
}