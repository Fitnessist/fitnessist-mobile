package com.capstone_bangkit.fitnessist.authentication

import android.content.Context

class AuthenticationManager(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    private val edit = preferences.edit()

    fun login(key: String, value: String) {
        edit.putString(key, value)
        edit.apply()
    }

    fun loginInt(key: String, value: Int) {
        edit.putInt(key, value)
        edit.apply()
    }

    fun setSession(key: String, value: Boolean) {
        edit.putBoolean(key, value)
        edit.apply()
    }

    fun checkSession(key: String): Boolean? {
        return preferences.getBoolean(key, false)
    }

    fun getAccess(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getAccessInt(key: String): Int? {
        return preferences.getInt(key, 0)
    }

    fun logOut() {
        edit.clear()
        edit.apply()
    }

    companion object {
        // Data User
        const val PREFS = "prefs"
        const val SESSION = "session"
        const val TOKEN = "token"
        const val EMAIL = "email"
        const val NAME = "name"
        const val USERNAME = "username"

        // Data TDEE Calculation
        const val GENDER = "gender"
        const val AGE = "age"
        const val WEIGHT = "weight"
        const val HEIGHT = "height"
        const val ACTIVITY = "activity"
        const val FAT = "fat"
        const val CALORIES_EACH_DAY = "calories_each_day"
        const val CALORIES_EACH_DAY_TARGET = "calories_each_day_target"
        const val PROGRAM_ID = "program_id"
        const val TDEE  = "tdee"
        const val WEIGHT_TARGET = "weight_target"
    }
}