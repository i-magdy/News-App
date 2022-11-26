package com.devwarex.news.ui.launch

import android.util.Log
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.repos.RefreshArticlesRepo
import com.devwarex.news.util.TimeoutUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import javax.inject.Inject

class LaunchRepo @Inject constructor(
    private val datastore: DatastoreImpl,
    private val refreshRepo: RefreshArticlesRepo
) {

    val uiState = MutableStateFlow(LaunchUiState())
    private var isRefreshing = true
    suspend fun checkState(
        isOnline: Boolean
    ){
        datastore.currentStep.collect{ step ->
            if (step < 4){
                delay(800)
                uiState.emit(LaunchUiState(isOffline = !isOnline, navigateToBoarding = true))
            }else{
                if (isOnline){
                    datastore.refreshTimeout.collect{ time ->
                        datastore.selectedCountry.collect { code ->
                            if (TimeoutUtil.isTimeout(Calendar.getInstance().timeInMillis, time)) {
                                if (isRefreshing){
                                    refreshRepo.sync(
                                        countryCode = code
                                    )
                                    datastore.updateRefreshTimeout(Calendar.getInstance().timeInMillis)
                                    delay(1800)
                                    uiState.emit(
                                        LaunchUiState(
                                            navigateToHome = true
                                        )
                                    )
                                }
                                isRefreshing = false
                            } else {
                                uiState.emit(
                                    LaunchUiState(
                                        navigateToHome = true
                                    )
                                )
                            }
                        }
                    }
                }else{ uiState.emit(LaunchUiState(
                    navigateToHome = true
                )) }

            }
        }
    }

    fun cancelJob() = refreshRepo.cancelJob()
}