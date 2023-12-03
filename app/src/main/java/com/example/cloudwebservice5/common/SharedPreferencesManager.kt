package com.example.cloudwebservice5.common

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesManager {

    private const val PREFERENCES_NAME = "user_preferences"

    fun getPreferences(mContext: Context): SharedPreferences {
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun clearPreferences(context: Context) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun setLoginInfo(context: Context, userId: String?) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString("userId", userId)
        editor.apply()
    }

    fun getLoginInfo(context: Context): Map<String, String?> {
        val prefs = getPreferences(context)
        val loginInfo: MutableMap<String, String?> = HashMap()
        val userId = prefs.getString("userId", "")
        loginInfo["userId"] = userId
        return loginInfo
    }

}