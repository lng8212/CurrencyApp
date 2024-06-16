package domain

import domain.model.Currency
import domain.model.RequestState
import kotlinx.coroutines.flow.Flow

/**
 * @Author: longkd
 * @Since: 11:44 - 16/06/2024
 */
interface MongoRepository {
    fun configureTheRealm()
    suspend fun insertCurrencyData(data: Currency)
    fun readCurrencyData(): Flow<RequestState<List<Currency>>>
    suspend fun cleanUp()
}