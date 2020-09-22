package com.sibi.budgetingapp.source.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.db.ExpenseDao
import com.sibi.budgetingapp.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExpenseRepository(val expenseDao: ExpenseDao) {

    private val dispose: CompositeDisposable = CompositeDisposable()
    val dataExpense = MutableLiveData<HashMap<String, ArrayList<Expense>>>()
    val dataTotalExpense = MutableLiveData<Int>()
    private var totalExpense = 0
    var dataTotalExpenseByType = MutableLiveData<Int>()
    var dataTotalExpenseAllType = MutableLiveData<HashMap<String, Int>>()
    val dataShowNotif = MutableLiveData<Boolean>()
    val dataTotalThisMonth = MutableLiveData<Pair<Int,Int>>()

    private var totalThisMonth = 0



    val dataUpdated = MutableLiveData<TypeUpdate>()

    init {
        getData()
    }


    fun getData() {

        var month = Calendar.getInstance().get(Calendar.MONTH)+1
        dispose.add(
            expenseDao.getAll().subscribeOn(Schedulers.computation()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ expenses ->
                val map = HashMap<String, Int>()
                println("cek update $expenses")
                val mapExpense = HashMap<String, ArrayList<Expense>>()
                val listHeader = ArrayList<String>()
                if (expenses.isEmpty()) {
                    totalExpense = 0
                }
                expenses.forEach{ expense ->
                    if(month.toString() == expense.date.split("/")[1]) {
                        totalThisMonth+=expense.amount
                    }
                    println("for each ${map[expense.type]} ${expense.amount}")
                    map.put(expense.type,(map[expense.type] ?: 0) + expense.amount)
                    println("iterate expense ${map[expense.type]}")
                    totalExpense += expense.amount


                    val key = formatDate(expense.date)
                    if (mapExpense.containsKey(key)) {
                        mapExpense[key]!!.add(expense);
                        mapExpense.put(key, mapExpense[key]!!)
                    } else {
                        val arrayExpense = ArrayList<Expense>()
                        arrayExpense.add(expense)
                        mapExpense.put(key, arrayExpense)
                    }
                }
                dataTotalThisMonth.postValue(Pair(month,totalThisMonth))
                totalThisMonth = 0
                dataTotalExpenseAllType.postValue(map)
                dataTotalExpense.value = totalExpense
                totalExpense = 0
                dataExpense.value = mapExpense
            }, {
                println("throwww ${it.message}")
            })
        )
    }

    fun insertData(expense: Expense) {
        dispose.add(
            expenseDao.insertExpense(expense).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                dataUpdated.value = TypeUpdate(0,expense.type,expense.amount)
                Log.d(TAG, "success insert data expense ${expense.type} ${expense.amount}")

            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun deleteData(expense: Expense) {
        dispose.add(
            expenseDao.deleteExpense(expense).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                Log.d(TAG, "success delete data expense ${expense.type} ${expense.amount}")
                dataUpdated.value = TypeUpdate(2,expense.type,expense.amount)
            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun updateData(expense: Expense) {
        dispose.add(
            expenseDao.updateExpense(expense).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                dataUpdated.value = TypeUpdate(1,expense.type,expense.amount)
                Log.d(TAG, "success update Data")
            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun getExpenseByType(type: String) {
        dispose.add(expenseDao.getTotalExpenseByType(type).defaultIfEmpty(0).subscribe({ total ->
            dataTotalExpenseByType.postValue(total)
        }, {
            Log.e(TAG, "failed get total expense by type")
        }))


    }



    fun dispose() {
        dispose.clear()
    }

}

data class TypeUpdate(val type:Int,val category : String, val value: Int)