package com.apx5.apx5.ui.days


/**
 * DaysNavigator
 */
interface DaysNavigator {
    fun searchOtherGame()
    fun saveGameToRemote()
    fun setRemoteGameData(show: Boolean)
    fun showSuccessDialog()
    fun cancelSpinKit()
    fun showDialogForDoubleHeader()
}
