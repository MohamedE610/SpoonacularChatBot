package com.example.spoonacularchatbot.core.data.local.sharedpref

import android.content.SharedPreferences
import com.example.spoonacularchatbot.core.presentation.di.qualifier.PreferenceInfo
import javax.inject.Inject


class UserPreference @Inject constructor(
    @PreferenceInfo private val sharedPreferences: SharedPreferences
) {

    fun saveUserName(name: String) {
        sharedPreferences.edit().putString(USER_NAME, name).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, "NONE") ?: "NONE"
    }

    companion object {
        const val USER_NAME = "user_name"
    }
}
