package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    repository: CurrencyRepository
) : ViewModel() {
    val accounts: StateFlow<List<Account>> = repository.getAccounts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
