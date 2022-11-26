package com.devwarex.news.ui.onBoarding.category

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devwarex.news.R
import com.devwarex.news.db.Category
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryScreen: Fragment(
    R.layout.screen_category
) {
    private val viewModel by viewModels<CategoryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chipGroup = view.findViewById<ChipGroup>(R.id.categories_chip_group)
        val finishButton = view.findViewById<MaterialButton>(R.id.finish_button)
        val lang =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0].language
        } else {
            resources.configuration.locale.language
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.categories.collect{createChips(it,chipGroup,lang = lang)}
                }
                launch { viewModel.count.collect{
                    finishButton.isEnabled = it == 3
                } }
            }
        }
        finishButton.setOnClickListener { viewModel.finish() }
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
                viewModel.updateSubscribedCategory(value = tag,isChecked = isChecked)
                buttonView.isChecked = !isChecked
            }
            chipGroup.addView(chip)
        }
    }
}