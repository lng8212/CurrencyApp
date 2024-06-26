package presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import data.remote.api.CurrencyApiServiceImpl
import domain.model.CurrencyType
import presentation.component.CurrencyPickerDialog
import presentation.component.HomeHeader
import surfaceColor

/**
 * @Author: longkd
 * @Since: 13:03 - 15/06/2024
 */
class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val rateStatus by viewModel.rateStatus
        val sourceCurrency by viewModel.sourceCurrency
        val targetCurrency by viewModel.targetCurrency
        val allCurrency = viewModel.allCurrencies
        val amount by rememberSaveable { mutableStateOf(0.0) }

        val selectedCurrencyType: CurrencyType by remember {
            mutableStateOf(CurrencyType.None)
        }
        var dialogOpened by remember { mutableStateOf(true) }

        if (dialogOpened) {
            CurrencyPickerDialog(currencies = allCurrency,
                currencyType = selectedCurrencyType,
                onPositiveClick = {
                    dialogOpened = false
                },
                onDismiss = {
                    dialogOpened = false
                })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
        ) {
            HomeHeader(
                status = rateStatus,
                amount = amount,
                source = sourceCurrency,
                target = targetCurrency,
                onRatesRefresh = {
                    viewModel.sendEvent(
                        HomeUiEvent.RefreshRates
                    )
                },
                onSwitchClick = {
                    viewModel.sendEvent(HomeUiEvent.SwitchCurrencies)
                },
                onAmountChange = {

                }
            )
        }
    }
}