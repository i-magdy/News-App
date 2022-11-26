package com.devwarex.news.ui.home.search

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devwarex.news.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesDialog : DialogFragment(
    R.layout.dialog_categories
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<SearchViewModel>()
        val group = view.findViewById<ChipGroup>(R.id.chip_group_dialog)
        val lang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0].language
        } else {
            resources.configuration.locale.language
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.categories.collect {
                    group.removeAllViews()
                    group.removeAllViewsInLayout()
                    it.forEach {
                        val chip = Chip(context)
                        chip.isCheckable = true
                        chip.isClickable = true
                        chip.tag = it.value
                        if (lang == "ar") {
                            chip.text = it.name_ar
                        } else {
                            chip.text = it.name_en
                        }
                        chip.setOnCheckedChangeListener { buttonView, isChecked ->
                            val tag = buttonView.tag as String
                            viewModel.setCategory(tag)
                            dismiss()
                        }
                        group.addView(chip)
                    }
                }
            }

        }
    }
}