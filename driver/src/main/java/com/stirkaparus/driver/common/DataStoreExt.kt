package com.stirkaparus.driver.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


const val DRIVER_STORE_NAME = "stirkaparus.driver"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DRIVER_STORE_NAME)
