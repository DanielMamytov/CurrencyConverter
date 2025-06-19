package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getAccounts(): Flow<List<Account>>
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun getRates(base: String, amount: Double): List<RateDto>
    suspend fun updateAccount(code: String, amount: Double)
    suspend fun addTransaction(transaction: Transaction)
}
