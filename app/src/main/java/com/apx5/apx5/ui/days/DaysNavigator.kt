package com.apx5.apx5.ui.days


import com.apx5.apx5.model.ResourceGame

/**
 * DaysNavigator
 */
interface DaysNavigator {
    fun searchOtherGame()
    fun saveGameToRemote()
    fun setRemoteGameData(game: ResourceGame)
    fun noGameToday()
    fun showSuccessDialog()
}
