package com.devwarex.news.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val repo: LaunchRepo
): ViewModel() {

    val uiState: StateFlow<LaunchUiState> = repo.uiState

    fun launch(
        isOnline: Boolean
    ) = viewModelScope.launch {
        repo.checkState(
            isOnline = isOnline
        )
    }
}