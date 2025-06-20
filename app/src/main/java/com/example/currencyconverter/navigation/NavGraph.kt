package com.example.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.presentation.screens.CurrenciesScreen
import com.example.currencyconverter.presentation.screens.ExchangeScreen
import com.example.currencyconverter.presentation.screens.TransactionsScreen

object Destinations {
    const val CURRENCIES = "currencies"
    const val EXCHANGE = "exchange"
    const val TRANSACTIONS = "transactions"
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Destinations.CURRENCIES) {
        composable(Destinations.CURRENCIES) {
            CurrenciesScreen(
                onExchange = { code -> navController.navigate("${Destinations.EXCHANGE}/$code") },
                onTransactions = { navController.navigate(Destinations.TRANSACTIONS) }
            )
        }
        composable("${Destinations.EXCHANGE}/{code}") { backStack ->
            val code = backStack.arguments?.getString("code") ?: "USD"
            ExchangeScreen(code) { navController.popBackStack(); Unit }
        }
        composable(Destinations.TRANSACTIONS) {
            TransactionsScreen { navController.popBackStack(); Unit }
        }
    }
}
