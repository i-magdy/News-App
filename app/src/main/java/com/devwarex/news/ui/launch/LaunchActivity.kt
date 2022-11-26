package com.devwarex.news.ui.launch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.devwarex.news.R
import com.devwarex.news.ui.BaseActivity
import com.devwarex.news.ui.home.HomeActivity
import com.devwarex.news.ui.onBoarding.OnBoardingActivity
import com.devwarex.news.util.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : BaseActivity(
    R.layout.activity_launch
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<LaunchViewModel>()
        viewModel.launch(NetworkUtil.isMobileConnectedToInternet(this))
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect{
                if (!it.isOffline && it.navigateToBoarding){
                    val i = Intent(this@LaunchActivity,OnBoardingActivity::class.java)
                    startActivity(i)
                    finish()
                }
                if (it.navigateToBoarding && it.isOffline){
                    Toast.makeText(this@LaunchActivity,getString(R.string.offline_message),Toast.LENGTH_LONG).show()
                }

                if (it.navigateToHome){
                    val i = Intent(this@LaunchActivity,HomeActivity::class.java)
                    viewModel.cancelJob()
                    startActivity(i)
                    finish()
                }
            }
        }
    }
}