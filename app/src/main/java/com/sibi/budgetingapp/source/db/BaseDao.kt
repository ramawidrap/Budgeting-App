package com.sibi.budgetingapp.source.db

import androidx.room.*
import com.sibi.budgetingapp.model.Income
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

//@Dao
//interface BaseDao<T :BaseModel> {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertIncome(data: T) : Completable
//
//    @Delete
//    fun deleteIncome(data: T) : Completable
//
//    @Update
//    fun updateIncome(data: T) : Completable
//
//////    @Query("select * from income")
////    fun getAll() : Flowable<List<T>>
////
//////    @Query("select sum(amount) from income")
////    fun totalAmount() : Single<Int>
//}