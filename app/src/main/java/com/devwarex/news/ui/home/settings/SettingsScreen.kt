package com.devwarex.news.ui.home.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devwarex.news.R
import com.devwarex.news.adapters.CountriesAdapter
import com.devwarex.news.db.Category
import com.devwarex.news.ui.onBoarding.category.CategoryViewModel
import com.devwarex.news.ui.onBoarding.country.CountryViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class SettingsScreen : Fragment(
    R.layout.screen_settings
) {

    private val categoriesViewModel by viewModels<CategoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countriesViewModel by viewModels<CountryViewModel>()
        val viewModel by viewModels<SettingsViewModel>()
        countriesViewModel.getCountries()
        val chipGroup = view.findViewById<ChipGroup>(R.id.categories_chip_group)
        val recyclerView = view.findViewById<RecyclerView>(R.id.countries_rv)
        val arabicButton = view.findViewById<MaterialButton>(R.id.arabic_button)
        val englishButton = view.findViewById<MaterialButton>(R.id.english_button)
        val categoryErrorTv = view.findViewById<TextView>(R.id.categories_error_tv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CountriesAdapter(object : CountriesAdapter.CountryListener{
            override fun onCountryClick(code: String) {
                countriesViewModel.updateSelectedCountry(code)
            }
        })
        recyclerView.adapter = adapter
        val lang =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0].language
        } else {
            resources.configuration.locale.language
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    categoriesViewModel.categories.collect{createChips(it,chipGroup,lang = lang)}
                }
                launch {
                    countriesViewModel.uiState.collect{
                        adapter.setCountries(it.countries,it.code)
                    }
                }
                launch {
                    if (lang == "ar") {
                        arabicButton.strokeWidth = 2
                        arabicButton.setTextColor(resources.getColor(R.color.blue_400, null))
                    } else {
                        englishButton.strokeWidth = 2
                        englishButton.setTextColor(resources.getColor(R.color.blue_400, null))
                    }
                }

                launch { categoriesViewModel.count.collect{
                    categoryErrorTv.visibility = if (it < 3) View.VISIBLE else View.GONE
                } }
            }
        }
        arabicButton.setOnClickListener { viewModel.changeAppLanguage("ar") }
        englishButton.setOnClickListener { viewModel.changeAppLanguage("en") }
    }

    private fun createChips(
        categories: List<Category>,
        chipGroup: ChipGroup,
        lang: String
    ){
        chipGroup.removeAllViews()
        chipGroup.removeAllViewsInLayout()
        categories.forEach {
            val chip = Chip(context)
            chip.isCheckable = true
            chip.isClickable = true
            chip.tag = it.value
            if (lang == "ar"){
                chip.text = it.name_ar
            }else{
                chip.text = it.name_en
            }
            if (it.isFollowed){
                chip.isChecked = true
                chip.setChipBackgroundColorResource(R.color.blue_200)
            }
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                val tag = buttonView.tag as String
                categoriesViewModel.updateSubscribedCategory(value = tag,isChecked = isChecked)
                buttonView.isChecked = !isChecked
            }
            chipGroup.addView(chip)
        }
    }
}