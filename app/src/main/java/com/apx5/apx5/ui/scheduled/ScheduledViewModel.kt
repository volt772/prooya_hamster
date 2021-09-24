package com.apx5.apx5.ui.scheduled

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.model.PrGame
import com.apx5.domain.model.PrGameSave
import com.apx5.domain.param.GameParam
import com.apx5.domain.param.GameSaveParam
import com.apx5.domain.usecase.ScheduledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ScheduledViewModel
 */

@HiltViewModel
class ScheduledViewModel @Inject constructor(
    private val scheduledUseCase: ScheduledUseCase
) : BaseViewModel()  {

    private val todayGame = MutableLiveData<PrResource<PrGame>>()
    private val newGame = MutableLiveData<PrResource<PrGameSave>>()

    /**
     * fetchMyGame
     * @desc 경기정보
     */
    fun fetchMyGame(param: GameParam) {
        viewModelScope.launch {
            todayGame.postValue(PrResource.loading(null))
            try {
                val result = scheduledUseCase.fetchDayGame(param)
                todayGame.postValue(PrResource.success(result))
            } catch (e: Exception) {
                todayGame.postValue(PrResource.error("[FAIL] Load Today Game", null))
            }
        }
    }

    /**
     * saveNewPlay
     * @desc 새기록 저장
     */
    fun saveNewPlay(param: GameSaveParam) {
        viewModelScope.launch {
            newGame.postValue(PrResource.loading(null))
            try {
                val result = scheduledUseCase.saveNewGame(param)
                newGame.postValue(PrResource.success(result))
            } catch (e: Exception) {
                newGame.postValue(PrResource.error("[FAIL] Save New Game", null))
            }
        }
    }

    fun getTodayGame(): LiveData<PrResource<PrGame>> = todayGame
    fun postNewGame(): LiveData<PrResource<PrGameSave>> = newGame
}