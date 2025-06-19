package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.dataSource.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ConverterDatabase =
        Room.databaseBuilder(
            context,
            ConverterDatabase::class.java,
            "converter.db"
        ).addCallback(ConverterDatabase.callback)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAccountDao(db: ConverterDatabase) = db.accountDao()

    @Provides
    fun provideTransactionDao(db: ConverterDatabase) = db.transactionDao()

    @Provides
    @Singleton
    fun provideRatesService(): RatesService = RemoteRatesServiceImpl()

    @Provides
    @Singleton
    fun provideRepository(
        service: RatesService,
        db: ConverterDatabase
    ): CurrencyRepository = CurrencyRepositoryImpl(service, db.accountDao(), db.transactionDao())
}
