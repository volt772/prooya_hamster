package com.apx5.apx5.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.ui.utils.UiUtils
import java.io.File
import javax.inject.Inject

/**
 * PrPreferenceImpl
 */
class PrPreferenceImpl @Inject constructor(context: Context): PrPreference {
    private val preferences: SharedPreferences

    init {
        val context = context.applicationContext
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override val userEmail: String?
        get() = preferences.getString(PrPrefKeys.MY_EMAIL, "")

    override val userTeam: String?
        get() = preferences.getString(PrPrefKeys.MY_TEAM, "")

    override val defaultYear: Int
        get() = preferences.getInt(PrPrefKeys.DEFAULT_SEASON_YEAR, UiUtils.currentYear)

    override fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).commit()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    override fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).commit()
    }

    override fun getString(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).commit()
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
                Context.MODE_PRIVATE).edit().clear().commit()
            File(dir, child).delete()
        }
    }
}