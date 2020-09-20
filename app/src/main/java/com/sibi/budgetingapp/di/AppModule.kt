package com.sibi.budgetingapp.di

import android.app.Application
import androidx.room.Room
import com.sibi.budgetingapp.source.db.AppDatabase
import com.sibi.budgetingapp.source.db.ExpenseDao
import com.sibi.budgetingapp.source.db.GeneralDao
import com.sibi.budgetingapp.source.db.IncomeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun  provideDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application.applicationContext,AppDatabase::class.java,"UserDB").build()
    }

    @Singleton
    @Provides
    fun provideIncomeDao(appDatabase: AppDatabase) : IncomeDao {
        return appDatabase.incomeDao()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase) : ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Singleton
    @Provides
    fun provideGeneralDao(appDatabase: AppDatabase) : GeneralDao {
        return appDatabase.generalDao()
    }
}