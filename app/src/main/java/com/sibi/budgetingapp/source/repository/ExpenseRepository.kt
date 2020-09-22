package com.sibi.budgetingapp.source.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.db.ExpenseDao
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
    private val map = HashMap<String, Int>()
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
                print("cek update")
                val mapExpense = HashMap<String, ArrayList<Expense>>()
                val listHeader = ArrayList<String>()
                if (expenses.isEmpty()) {
                    totalExpense = 0
                }
                expenses.map { expense ->
                    totalExpense += expense.amount
//                    val notifInt = sharedPref.getInt(expense.type, -1)
//                    if (notifInt != -1) {
//
//                        if (notifInt - expense.amount <= 0) {
//                            dataShowNotif.postValue(true)
//                            sharedPref.edit()
//                                .putInt(expense.type, 100 - Math.abs(notifInt - expense.amount))
//                                .apply()
//                            dataShowNotif.postValue(false)
//                        } else {
//                            sharedPref.edit().putInt(expense.type, notifInt - expense.amount)
//                                .apply()
//                        }
//                    }



                    if (mapExpense.containsKey(expense.date)) {
                        mapExpense[expense.date]!!.add(expense);
                        mapExpense.put(expense.date, mapExpense[expense.date]!!)
                    } else {
                        val arrayExpense = ArrayList<Expense>()
                        arrayExpense.add(expense)
                        mapExpense.put(expense.date, arrayExpense)
                    }
                }
                dataTotalExpense.value = totalExpense
                totalExpense = 0
                dataExpense.value = mapExpense
            }, {
                println("throwww")
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



    fun getExpenseAllType() {
        println("get expense all type ")
        val types = listOf("Shopping", "Entertainment", "Transportation", "Foods", "Others")
        for (type in types) {
            dispose.add(
                expenseDao.getTotalExpenseByType(type).defaultIfEmpty(0).subscribe({ total ->
                    map.put(type, total);
                }, {
                    Log.e(TAG, "failed get total expense by type")
                })
            )
        }

        dataTotalExpenseAllType.postValue(map)

    }


    fun dispose() {
        dispose.clear()
    }

}