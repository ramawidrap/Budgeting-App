package com.sibi.budgetingapp.source.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sibi.budgetingapp.model.Income
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface IncomeDao {

    @Insert(onConflict = REPLACE)
    fun insertIncome(income: Income) : Completable

    @Delete
    fun deleteIncome(income: Income) : Completable

    @Update
    fun updateIncome(income:Income) : Completable

    @Query("select * from income")
    fun getAll() : Flowable<List<Income>>

    @Query("select sum(amount) from income")
    fun totalAmount() : Flowable<Int>

}