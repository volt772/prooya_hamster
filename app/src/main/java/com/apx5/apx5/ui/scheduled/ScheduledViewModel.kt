package com.apx5.apx5.ui.scheduled

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ScheduledViewModel
 */

@HiltViewModel
class ScheduledViewModel @Inject constructor(
    private val prRepository: PrRepository
) : BaseViewModel<Any>()  {

    private val todayGame = MutableLiveData<PrResource<CtGetPlay>>()
    private val newGame = MutableLiveData<PrResource<CtPostPlay>>()

    /**
     * fetchMyGame
     * @desc 경기정보
     */
    fun fetchMyGame(play: PtGetPlay) {
        viewModelScope.launch {
            todayGame.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getDayPlay(play)
                todayGame.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                todayGame.postValue(PrResource.error("[FAIL] Load Today Game", null))
            }
        }
    }

    /**
     * saveNewPlay
     * @desc 새기록 저장
     */
    fun saveNewPlay(play: PtPostPlay) {
        viewModelScope.launch {
            newGame.postValue(PrResource.loading(null))
            try {
                val result = prRepository.postNewGame(play)
                newGame.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                newGame.postValue(PrResource.error("[FAIL] Save New Game", null))
            }
        }
    }

    fun getTodayGame(): LiveData<PrResource<CtGetPlay>> = todayGame
    fun postNewGame(): LiveData<PrResource<CtPostPlay>> = newGame
}