package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.viewmodel.TransactionsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon

@Composable
fun TransactionsScreen(
    onBack: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    LazyColumn {
        item {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
        }
        items(viewModel.transactions.value) { tr ->
            ListItem(
                headlineContent = { Text("${tr.from} -> ${tr.to}") },
                supportingContent = { Text("${tr.fromAmount} to ${tr.toAmount}") }
            )
        }
    }
}
