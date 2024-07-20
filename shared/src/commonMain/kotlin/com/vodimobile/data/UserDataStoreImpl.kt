package com.vodimobile.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vodimobile.domain.repository.data_store.UserDataStore
import com.vodimobile.domain.model.User
import com.vodimobile.utils.data_store.Constants

class UserDataStoreImpl(private val dataStore: DataStore<Preferences>) : UserDataStore {
    override suspend fun editUserData(user: User) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_FULL_NAME)] = user.fullName
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_EMAIL)] = user.email ?: ""
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_TOKEN)] = user.token
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] = user.password
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PHONE)] = user.phone
        }
    }

}