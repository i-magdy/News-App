package com.devwarex.news.ui.onBoarding.country

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devwarex.news.R
import com.devwarex.news.adapters.CountriesAdapter
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryScreen : Fragment(
    R.layout.screen_country
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<CountryViewModel>()
        viewModel.getCountries()
        var isCountrySelected = false
        val selectedCountryTv = view.findViewById<TextView>(R.id.selected_country_tv)
        val adapter = CountriesAdapter(object : CountriesAdapter.CountryListener{
            override fun onCountryClick(code: String) {
                viewModel.updateSelectedCountry(code)
            }
        })
        view.findViewById<RecyclerView>(R.id.countries_rv).apply {
            this.layoutManager = GridLayoutManager(context,2)
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    adapter.setCountries(it.countries,it.code)
                    val lang =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        resources.configuration.locales[0].language
                    } else {
                        resources.configuration.locale.language
                    }
                    selectedCountryTv.text = it.selectedCountry?.let { country ->
                        country.flagSymbol + " " + if (lang == "ar") country.name_ar else country.name_en
                    }
                    isCountrySelected = it.selectedCountry != null
                }
            }
        }
        view.findViewById<MaterialButton>(
            R.id.next_button
        ).setOnClickListener {
            if (isCountrySelected) {
                viewModel.next()
                Navigation.findNavController(view).navigate(R.id.action_navigate_to_category_screen)
            }
        }
    }
}