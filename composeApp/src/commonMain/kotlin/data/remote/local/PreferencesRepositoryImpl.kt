package data.remote.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import domain.PreferencesRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * @Author: longkd
 * @Since: 16:24 - 15/06/2024
 */
@OptIn(ExperimentalSettingsApi::class)
class PreferencesRepositoryImpl(val settings: Settings) : PreferencesRepository {
    companion object {
        const val TIMESTAMP_KEY = "lastUpdated"
    }

    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()
    override suspend fun saveLastUpdated(lastUpdated: String) {
        flowSettings.putLong(
            key = TIMESTAMP_KEY,
            value = Instant.parse(lastUpdated).toEpochMilliseconds()
        )
    }

    override suspend fun isDataFetch(currentTimeStamp: Long): Boolean {
        val savedTimeStamp = flowSettings.getLong(
            key = TIMESTAMP_KEY,
            defaultValue = 0
        )
        return if (savedTimeStamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimeStamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimeStamp)
            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
            daysDifference < 1
        } else
            false
    }
}