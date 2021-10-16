package com.example.homecreditbank.shop.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class LocalAuthRepository(private val preferences: SharedPreferences) {

    companion object {
        private const val KET_SAVED_IS_AUTHORIZED = "is_authorized"
    }

    fun saveAuthorizationState(isAuthorized: Boolean) = preferences.edit {
        putBoolean(KET_SAVED_IS_AUTHORIZED, isAuthorized)
    }

    fun getAuthorizationState() = preferences.getBoolean(KET_SAVED_IS_AUTHORIZED, false)
}
