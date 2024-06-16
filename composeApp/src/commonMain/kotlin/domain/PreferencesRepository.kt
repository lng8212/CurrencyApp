package domain

/**
 * @Author: longkd
 * @Since: 16:22 - 15/06/2024
 */

interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFetch(currentTimeStamp: Long): Boolean
}