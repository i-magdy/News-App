package com.devwarex.news.ui.onBoarding

import androidx.lifecycle.ViewModel
import com.devwarex.news.data.DatastoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val datastore: DatastoreImpl
): ViewModel() {

    val currentStep: Flow<Int> = datastore.currentStep
}