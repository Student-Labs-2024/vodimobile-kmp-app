package com.vodimobile.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vodimobile.domain.repository.data_store.UserDataStoreRepository
import com.vodimobile.domain.model.User
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import com.vodimobile.utils.data_store.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    UserDataStoreRepository {

    val userFromFlow: Flow<User> = dataStore.data.map {
        User(
            id = it[intPreferencesKey(Constants.DATA_STORE_USER_ID)] ?: -1,
            fullName = it[stringPreferencesKey(Constants.DATA_STORE_USER_FULL_NAME)] ?: "",
            password = it[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] ?: "",
            accessToken = it[stringPreferencesKey(Constants.DATA_STORE_USER_ACCESS_TOKEN)] ?: "",
            refreshToken = it[stringPreferencesKey(Constants.DATA_STORE_USER_REFRESH_TOKEN)] ?: "",
            phone = it[stringPreferencesKey(Constants.DATA_STORE_USER_PHONE)] ?: "",
        )
    }

    override suspend fun editUserData(user: User) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_FULL_NAME)] = user.fullName
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] = user.password
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PHONE)] = user.phone
        }
    }

    override fun getUserData(): Flow<User> {
        val userFlow: Flow<User> = dataStore.data.map { preferences ->
            val id = preferences[intPreferencesKey(Constants.DATA_STORE_USER_ID)] ?: -1
            val fullName =
                preferences[stringPreferencesKey(Constants.DATA_STORE_USER_FULL_NAME)] ?: ""
            val accessToken =
                preferences[stringPreferencesKey(Constants.DATA_STORE_USER_ACCESS_TOKEN)] ?: SharedBuildkonfig.crm_test_access_token
            val refreshToken =
                preferences[stringPreferencesKey(Constants.DATA_STORE_USER_REFRESH_TOKEN)] ?: SharedBuildkonfig.crm_test_refresh_token
            val password =
                preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] ?: ""
            val phone = preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PHONE)] ?: ""

            return@map User(
                id = id,
                fullName = fullName,
                password = password,
                accessToken = accessToken,
                refreshToken = refreshToken,
                phone = phone,
            )
        }

        return userFlow
    }

    override suspend fun editPreregister(name: String, password: String, accessToken: String, refreshToken: String, expired: Long) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_FULL_NAME)] = name
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] = password
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_ACCESS_TOKEN)] = accessToken
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_REFRESH_TOKEN)] = refreshToken
            preferences[longPreferencesKey(Constants.DATA_STORE_USER_EXPIRES_TOKEN)] = expired
        }
    }

    override suspend fun editPassword(password: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_PASSWORD)] = password
        }
    }

    override suspend fun editTokens(accessToken: String, refreshToken: String, expires: Long) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_ACCESS_TOKEN)] = accessToken
            preferences[stringPreferencesKey(Constants.DATA_STORE_USER_REFRESH_TOKEN)] =
                refreshToken
            preferences[longPreferencesKey(Constants.DATA_STORE_USER_EXPIRES_TOKEN)] = expires
        }
    }
}