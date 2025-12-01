package com.example.myapplication.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object AuthKeys {
    val TOKEN = stringPreferencesKey("token")
    val REMEMBER_ME = booleanPreferencesKey("remember_me")
}
