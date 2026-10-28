package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDataSource {

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    override suspend fun getToken(): String? {
        return try {
            dataStore.data
                .map { preferences -> preferences[AUTH_TOKEN_KEY] }
                .first()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }
}
