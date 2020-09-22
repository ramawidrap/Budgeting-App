package com.sibi.budgetingapp.source.db

import androidx.room.*
import com.sibi.budgetingapp.model.Expense
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(income: Expense) : Completable

    @Delete
    fun deleteExpense(income: Expense) : Completable

    @Update
    fun updateExpense(income: Expense) : Completable

    @Query("select * from expense")
    fun getAll() : Flowable<List<Expense>>

    @Query("select sum(amount) from expense")
    fun totalAmount() : Flowable<Int>

    @Query("select sum(amount) from expense where type=:type ")
    fun getTotalExpenseByType(type:String) : Flowable<Int>
}