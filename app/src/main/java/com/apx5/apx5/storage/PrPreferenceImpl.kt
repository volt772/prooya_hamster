package com.apx5.apx5.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.ui.utilities.PrUtils
import java.io.File
import javax.inject.Inject

/**
 * PrPreferenceImpl
 */

class PrPreferenceImpl @Inject constructor(
    context: Context,
    val prUtils: PrUtils
): PrPreference {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override val userEmail: String?
        get() = preferences.getString(PrPrefKeys.MY_EMAIL, "")

    override val userTeam: String?
        get() = preferences.getString(PrPrefKeys.MY_TEAM, "")

    override val defaultYear: Int
        get() = preferences.getInt(PrPrefKeys.DEFAULT_SEASON_YEAR, prUtils.currentYear)

    override fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    override fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    override fun removePref(context: Context) {
        val dir = File(context.filesDir.parent + "/shared_prefs/")
        val children = dir.list()
        for (child in children) {
            context.getSharedPreferences(
                child.replace(".xml", ""),
                Context.MODE_PRIVATE).edit().clear().apply()
            File(dir, child).delete()
        }
    }
}