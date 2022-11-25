package com.devwarex.news.ui.onBoarding.wecome

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devwarex.news.R
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeScreen : Fragment(
    R.layout.screen_welcome
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arabicButton = view.findViewById<MaterialButton>(R.id.arabic_button)
        val englishButton = view.findViewById<MaterialButton>(R.id.english_button)
        val startButton = view.findViewById<MaterialButton>(R.id.start_button)
        var lang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0].language
        } else {
            resources.configuration.locale.language
        }
        val viewModel by viewModels<WelcomeViewModel>()
        lifecycleScope.launchWhenCreated {
            viewModel.language.collect{
                if(it.isNotEmpty()){ lang = it }
                if (lang == "ar"){
                    arabicButton.strokeWidth = 2
                    arabicButton.setTextColor(resources.getColor(R.color.blue_400,null))
                }else{
                    englishButton.strokeWidth = 2
                    englishButton.setTextColor(resources.getColor(R.color.blue_400,null))
                }
            }
        }
        val phone = requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        viewModel.updateCountry(
            country = phone.networkCountryIso
        )
        arabicButton.setOnClickListener { viewModel.changeAppLanguage("ar") }
        englishButton.setOnClickListener { viewModel.changeAppLanguage("en") }
        startButton.setOnClickListener { viewModel.start() }
    }
}