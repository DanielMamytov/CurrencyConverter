package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.model.Transaction
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _rates = MutableStateFlow<List<RateDto>>(emptyList())
    val rates: StateFlow<List<RateDto>> = _rates

    fun loadRates(code: String, amount: Double) {
        viewModelScope.launch {
            _rates.value = repository.getRates(code, amount)
        }
    }

    fun exchange(from: String, to: String, fromAmount: Double, toAmount: Double) {
        viewModelScope.launch {
            repository.updateAccount(from, fromAmount)
            repository.updateAccount(to, toAmount)
            repository.addTransaction(
                Transaction(
                    from = from,
                    to = to,
                    fromAmount = fromAmount,
                    toAmount = toAmount,
                    dateTime = LocalDateTime.now()
                )
            )
        }
    }
}
