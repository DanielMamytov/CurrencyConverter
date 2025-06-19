package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.viewmodel.CurrenciesViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List

@Composable
fun CurrenciesScreen(
    onExchange: (String) -> Unit,
    onTransactions: () -> Unit,
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    Column {
        IconButton(onClick = onTransactions) {
            Icon(Icons.Default.List, contentDescription = null)
        }
        LazyColumn {
            items(viewModel.accounts.value) { account ->
                ListItem(
                    headlineContent = { Text(account.code) },
                    supportingContent = { Text(account.amount.toString()) },
                    modifier = androidx.compose.ui.Modifier.clickable { onExchange(account.code) }
                )
            }
        }
    }
}
