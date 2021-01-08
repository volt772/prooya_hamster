package com.apx5.apx5.ui.statics


import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.ops.OpsUser

/**
 * StaticsNavigator
 */
interface StaticsNavigator {
    fun saveUserInfo(user: OpsUser)
    fun cancelSpinKit()
    fun showSuccessDialog()
    fun setDatumChart(statics: DtStatics)
}
