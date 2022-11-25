package com.devwarex.news.ui.launch

import com.devwarex.news.data.DatastoreImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LaunchRepo @Inject constructor(
    private val datastore: DatastoreImpl
) {

    val uiState = MutableStateFlow(LaunchUiState())

    suspend fun checkState(
        isOnline: Boolean
    ){
        datastore.currentStep.collect{ step ->
            if (step < 4){
                delay(800)
                uiState.emit(LaunchUiState(isOffline = !isOnline, navigateToBoarding = true))
            }else{
                uiState.emit(LaunchUiState(navigateToHome = true))
            }
        }
    }
}