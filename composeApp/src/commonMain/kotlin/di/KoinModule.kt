package di

import com.russhwolf.settings.Settings
import data.remote.api.CurrencyApiServiceImpl
import data.remote.local.MongoRepositoryImpl
import data.remote.local.PreferencesRepositoryImpl
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferencesRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.HomeViewModel

/**
 * @Author: longkd
 * @Since: 16:31 - 15/06/2024
 */


val appModule = module {
    single { Settings() }
    single<PreferencesRepository> { PreferencesRepositoryImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferencesRepository = get()) }
    single<MongoRepository> { MongoRepositoryImpl() }
    factory {
        HomeViewModel(
            preferences = get(),
            api = get(),
            mongoRepository = get()
        )
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}