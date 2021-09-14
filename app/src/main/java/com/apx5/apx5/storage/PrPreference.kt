package com.apx5.apx5.storage

import android.content.Context

/**
 * PrPreference
 */

interface PrPreference {

    val userEmail: String?

    val userTeam: String?

    val defaultYear: Int

    fun setInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int?

    fun setString(key: String, value: String)

    fun getString(key: String, defaultValue: String): String?

    fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun removePref(context: Context)
}
