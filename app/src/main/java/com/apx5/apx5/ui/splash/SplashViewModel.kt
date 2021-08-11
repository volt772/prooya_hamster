package com.apx5.apx5.ui.splash

import android.app.Application
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtPing
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.storage.PrefManager
import kotlinx.coroutines.launch

/**
 * TemplatesViewModel
 */

class SplashViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<SplashNavigator>() {

    private val serverStatus = MutableLiveData<PrResource<CtPing>>()

    init {
        checkServerStatus()
    }

    /* 화면 표기 및 사용검사*/
    internal fun startSeeding() {
        val DURATION = 1000
        Handler().postDelayed({
            checkAccountAndDecideNextActivity()
        }, DURATION.toLong())
    }

    private fun moveToDashBoard() {
        getNavigator()?.switchToDashBoard()
    }

    private fun moveToLogin() {
        getNavigator()?.switchToLogin()
    }

    /* Next Activity 검사*/
    private fun checkAccountAndDecideNextActivity() {
        val email = PrefManager.getInstance(appContext).userEmail

        email?.let { _email ->
            if (_email.isNotBlank() && _email.contains("@")) {
                moveToDashBoard()
            } else {
                moveToLogin()
            }
        } ?: run {
            moveToLogin()
        }
    }

    /* 서버 검사*/
    private fun checkServerStatus() {
        viewModelScope.launch {
            serverStatus.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getServerStatus()
                val serverResult = PrResource.success(result.data)

                serverStatus.postValue(serverResult)
            } catch (e: Exception) {
                serverStatus.postValue(PrResource.error("Server Check Error", null))
            }
        }
    }

    fun getServerStatus(): LiveData<PrResource<CtPing>> = serverStatus
}