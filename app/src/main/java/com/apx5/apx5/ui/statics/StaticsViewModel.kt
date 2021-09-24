package com.apx5.apx5.ui.statics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.model.PrStatics
import com.apx5.domain.param.StaticsParam
import com.apx5.domain.usecase.StaticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * StaticsViewModel
 */

@HiltViewModel
class StaticsViewModel @Inject constructor(
    private val staticsUseCase: StaticsUseCase
) : BaseViewModel() {

    private val statics = MutableLiveData<PrResource<PrStatics>>()

    /**
     * fetchStatics
     * @desc 통계데이터 다운로드
     */
    fun fetchStatics(userEmail: String) {
        viewModelScope.launch {
            statics.postValue(PrResource.loading(null))
            try {
                val result = staticsUseCase.fetchStatics(StaticsParam(userEmail))
                statics.postValue(PrResource.success(result))
            } catch (e: Exception) {
                statics.postValue(PrResource.error("[FAIL] Load All Statics", null))
            }
        }
    }

    fun getStatics(): LiveData<PrResource<PrStatics>> = statics
}