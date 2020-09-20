package com.sibi.budgetingapp.source.db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GeneralDao {
    @Query("select (select sum(amount) from income) - (select sum(amount) from expense) ")
    fun getBalance() : Single<Int>
}