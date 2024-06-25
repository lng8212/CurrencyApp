package domain

import domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

/**
 * @Author: longkd
 * @Since: 16:22 - 15/06/2024
 */

interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFetch(currentTimeStamp: Long): Boolean
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)
    fun readSourceCurrencyCode(): Flow<CurrencyCode>
    fun readTargetCurrencyCode(): Flow<CurrencyCode>
}