package com.apx5.apx5.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.apx5.apx5.constants.PrPrefKeys
import java.io.File

/**
 * PrefManager
 */

class PrefManager private constructor(ctx: Context) {
    private val preferences: SharedPreferences

    val userEmail: String?
        get() = preferences.getString(PrPrefKeys.MY_EMAIL, "")

    val userTeam: String?
        get() = preferences.getString(PrPrefKeys.MY_TEAM, "")

    init {
        val context = ctx.applicationContext
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).commit()
    }

    fun getInt(key: String, defaultValue: Int): Int? {
        return preferences.getInt(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).commit()
    }

    fun getString(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).commit()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun removePref(context: Context) {
        val dir = File(context.filesDir.parent + "/shared_prefs/")
        val children = dir.list()
        for (child in children) {
            context.getSharedPreferences(
                child.replace(".xml", ""),
                Context.MODE_PRIVATE).edit().clear().commit()
            File(dir, child).delete()
        }
    }

    companion object {
        fun getInstance(ctx: Context): PrefManager {
            return PrefManager(ctx)
        }
    }
}
