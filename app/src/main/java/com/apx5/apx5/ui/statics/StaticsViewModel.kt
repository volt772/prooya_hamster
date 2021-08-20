package com.apx5.apx5.ui.statics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * StaticsViewModel
 */

@HiltViewModel
class StaticsViewModel @Inject constructor(
    private val repository: PrRepository
) : BaseViewModel<Any>() {

    private val statics = MutableLiveData<PrResource<CtPostStatics>>()

    /* 통계데이터 다운로드*/
    fun fetchStatics(userEmail: String) {
        viewModelScope.launch {
            statics.postValue(PrResource.loading(null))
            try {
                val result = repository.getStatics(PtPostStatics(userEmail))
                statics.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                statics.postValue(PrResource.error("Fetch Statics Datum Error", null))
            }
        }
    }

    fun getStatics(): LiveData<PrResource<CtPostStatics>> = statics
}
