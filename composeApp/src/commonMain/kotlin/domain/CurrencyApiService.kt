package domain

import domain.model.Currency
import domain.model.RequestState

/**
 * @Author: longkd
 * @Since: 12:54 - 15/06/2024
 */

interface CurrencyApiService {
    suspend fun getLatestExchangeRates(): RequestState<List<Currency>>
}