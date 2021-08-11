package com.apx5.apx5.ui.statics

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.ui.splash.SplashNavigator
import kotlinx.coroutines.launch

/**
 * StaticsViewModel
 */

class StaticsViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<Any>() {

    private val statics = MutableLiveData<PrResource<CtPostStatics>>()

    /* 통계데이터 다운로드*/
    internal fun getStatics(userEmail: String) {
        viewModelScope.launch {
            statics.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getStatics(PtPostStatics(userEmail))
                statics.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                statics.postValue(PrResource.error("Fetch Statics Datum Error", null))
            }
        }
    }

    fun getStatics(): LiveData<PrResource<CtPostStatics>> = statics
}
