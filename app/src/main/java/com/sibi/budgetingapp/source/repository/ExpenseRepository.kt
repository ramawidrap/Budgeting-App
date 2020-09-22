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
import javax.inject.Inject

class ExpenseRepository(val expenseDao: ExpenseDao) {

    private val dispose: CompositeDisposable = CompositeDisposable()
    val dataExpense = MutableLiveData<HashMap<String, ArrayList<Expense>>>()
    val dataTotalExpense = MutableLiveData<Int>()
    private var totalExpense = 0
    var dataTotalExpenseByType = MutableLiveData<Int>()
    var dataTotalExpenseAllType = MutableLiveData<HashMap<String, Int>>()
    val dataShowNotif = MutableLiveData<Boolean>()



    val dataUpdated = MutableLiveData<Pair<String,Int>>()

    init {
        getData()
    }


    fun getData() {
//        val sharedPref = context.getSharedPreferences("BUDGET", Context.MODE_PRIVATE)
//        val budgetStr = sharedPref.getString("budget", "")


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
                    println("for each ${map[expense.type]} ${expense.amount}")
                    map.put(expense.type,(map[expense.type] ?: 0) + expense.amount)
                    println("iterate expense ${map[expense.type]}")
                    totalExpense += expense.amount



                    val key = formatDate(expense.date)
                    println("cek key expense brok di convert")
                    if (mapExpense.containsKey(key)) {
                        mapExpense[key]!!.add(expense);
                        mapExpense.put(key, mapExpense[key]!!)
                    } else {
                        val arrayExpense = ArrayList<Expense>()
                        arrayExpense.add(expense)
                        mapExpense.put(key, arrayExpense)
                    }
                }
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
                dataUpdated.value = Pair(expense.type,expense.amount)
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
                dataUpdated.value = Pair(expense.type,-expense.amount)
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
                dataUpdated.value = Pair(expense.type,expense.amount)
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



//    fun getExpenseAllType() {
//        println("get expense all type ")
//        val types = listOf("Shopping", "Entertainment", "Transportation", "Foods", "Others")
//        dispose.add(
//            expenseDao.getTotalExpenseByType("Shopping").defaultIfEmpty(0).subscribe({ total ->
//                println("cek expanse all type Shopping, $total")
//                map.put("Shopping", total);
//            }, {
//                Log.e(TAG, "failed get total expense by type")
//            })
//        )
//
//        dispose.add(
//            expenseDao.getTotalExpenseByType("Entertainment").defaultIfEmpty(0).subscribe({ total ->
//                println("cek expanse all type Entertainment, $total")
//                map.put("Entertainment", total);
//            }, {
//                Log.e(TAG, "failed get total expense by type")
//            })
//        )
//
//        dispose.add(
//            expenseDao.getTotalExpenseByType("Transportation").defaultIfEmpty(0).subscribe({ total ->
//                println("cek expanse all type Shopping, $total")
//                map.put("Shopping", total);
//            }, {
//                Log.e(TAG, "failed get total expense by type")
//            })
//        )
//
//        dispose.add(
//            expenseDao.getTotalExpenseByType("Shopping").defaultIfEmpty(0).subscribe({ total ->
//                println("cek expanse all type Shopping, $total")
//                map.put("Shopping", total);
//            }, {
//                Log.e(TAG, "failed get total expense by type")
//            })
//        )
//
//        dispose.add(
//            expenseDao.getTotalExpenseByType("Shopping").defaultIfEmpty(0).subscribe({ total ->
//                println("cek expanse all type Shopping, $total")
//                map.put("Shopping", total);
//            }, {
//                Log.e(TAG, "failed get total expense by type")
//            })
//        )
//
//        dataTotalExpenseAllType.postValue(map)
//
//    }


    fun dispose() {
        dispose.clear()
    }

}