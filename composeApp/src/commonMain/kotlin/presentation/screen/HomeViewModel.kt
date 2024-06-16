package presentation.screen

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferencesRepository
import domain.model.Currency
import domain.model.RateStatus
import domain.model.RequestState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * @Author: longkd
 * @Since: 17:02 - 15/06/2024
 */

sealed class HomeUiEvent {
    data object RefreshRates : HomeUiEvent()
}

class HomeViewModel(
    private val preferences: PreferencesRepository,
    private val api: CurrencyApiService,
    private val mongoRepository: MongoRepository,
) : ScreenModel {
    private var _rateStatus: MutableState<RateStatus> =
        mutableStateOf(RateStatus.Idle)
    val rateStatus: State<RateStatus> = _rateStatus

    private var _allCurrencies = mutableStateListOf<Currency>()
    val allCurrencies: List<Currency> = _allCurrencies
    private var _sourceCurrency: MutableState<RequestState<Currency>> =
        mutableStateOf(RequestState.Idle)
    val sourceCurrency: State<RequestState<Currency>> = _sourceCurrency

    private var _targetCurrency: MutableState<RequestState<Currency>> =
        mutableStateOf(RequestState.Idle)
    val targetCurrency: State<RequestState<Currency>> = _targetCurrency

    init {
        screenModelScope.launch {
            fetchNewRates()
            getRateStatus()
        }
    }

    fun sendEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.RefreshRates -> {
                screenModelScope.launch {
                    fetchNewRates()
                }
            }
        }
    }

    private suspend fun fetchNewRates() {
        try {
            val localCache = mongoRepository.readCurrencyData().first()
            if (localCache.isSuccess()) {
                if (localCache.getSuccessData().isNotEmpty()) {
                    println("HomeViewModel: DATABASE IS FULL")
                    _allCurrencies.clear()
                    _allCurrencies.addAll(localCache.getSuccessData())
                    if (!preferences.isDataFetch(Clock.System.now().toEpochMilliseconds())) {
                        println("HomeViewModel: DATA NOT FRESH")
                        cacheTheData()
                    }
                    else{
                        println("HomeViewModel: DATA IS FRESH")
                    }
                } else {
                    println("HomeViewModel: DATABASE NEEDS DATA")
                    cacheTheData()
                }
            }
            else if (localCache.isError()) {
                println("HomeViewModel: ERROR READING LOCAL DATABASE ${localCache.getErrorMessage()}")
            }
            getRateStatus()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private suspend fun cacheTheData() {
        val fetchedData = api.getLatestExchangeRates()
        if (fetchedData.isSuccess()) {
            mongoRepository.cleanUp()
            fetchedData.getSuccessData().forEach {
                println("HomeViewModel: ADDING ${it.code}")
                mongoRepository.insertCurrencyData(it)
            }
            println("HomeViewModel: UPDATING _allCurrencies")
            _allCurrencies.clear()
            _allCurrencies.addAll(fetchedData.getSuccessData())
        } else if (fetchedData.isError()) {
            println("HomeViewModel: FETCHING FAILED ${fetchedData.getErrorMessage()}")
        }
    }

    private suspend fun getRateStatus() {
        _rateStatus.value = if (preferences.isDataFetch(
                currentTimeStamp = Clock.System.now().toEpochMilliseconds()
            )
        ) RateStatus.Fresh
        else RateStatus.Stale
    }

}