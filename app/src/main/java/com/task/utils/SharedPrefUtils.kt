package com.task.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtils(activity: Context) {
    private val sharedPref: SharedPreferences =
        activity.getSharedPreferences("preferenceName", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, default: String = ""): String {
        return sharedPref.getString(key, default).toString()
    }
}
