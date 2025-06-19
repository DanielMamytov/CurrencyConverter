package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.viewmodel.ExchangeViewModel

@Composable
fun ExchangeScreen(
    code: String,
    onDone: () -> Unit,
    viewModel: ExchangeViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf(1.0) }
    val rates by viewModel.rates.collectAsState()
    val rate = rates.firstOrNull { it.currency != code }
    LaunchedEffect(code, amount) { viewModel.loadRates(code, amount) }
    Column {
        Text("Base: $code")
        ListItem(headlineContent = { Text("Amount") }, supportingContent = { Text(amount.toString()) })
        rate?.let {
            Text("${it.currency}: ${it.value}")
        }
        Button(onClick = {
            rate?.let {
                viewModel.exchange(code, it.currency, amount, it.value)
                onDone()
            }
        }) { Text("Buy") }
    }
}
