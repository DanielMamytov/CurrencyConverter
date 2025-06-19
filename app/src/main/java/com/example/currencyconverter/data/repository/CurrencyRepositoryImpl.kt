package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.example.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Transaction
import com.example.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class CurrencyRepositoryImpl(
    private val ratesService: RatesService,
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : CurrencyRepository {

    override fun getAccounts(): Flow<List<Account>> =
        accountDao.getAllAsFlow().map { list ->
            list.map { Account(it.code, it.amount) }
        }

    override fun getTransactions(): Flow<List<Transaction>> =
        kotlinx.coroutines.flow.flow {
            emit(transactionDao.getAll().map {
                Transaction(
                    it.id,
                    it.from,
                    it.to,
                    it.fromAmount,
                    it.toAmount,
                    it.dateTime
                )
            })
        }

    override suspend fun getRates(base: String, amount: Double): List<RateDto> =
        ratesService.getRates(base, amount)

    override suspend fun updateAccount(code: String, amount: Double) {
        accountDao.insertAll(AccountDbo(code, amount))
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertAll(
            TransactionDbo(
                0,
                transaction.from,
                transaction.to,
                transaction.fromAmount,
                transaction.toAmount,
                transaction.dateTime
            )
        )
    }
}
