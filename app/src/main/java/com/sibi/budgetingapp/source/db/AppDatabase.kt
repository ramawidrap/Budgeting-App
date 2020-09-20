package com.sibi.budgetingapp.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.model.Income

@Database(entities = [Expense::class, Income::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun incomeDao() : IncomeDao
    abstract fun expenseDao() : ExpenseDao
    abstract fun generalDao() : GeneralDao


}