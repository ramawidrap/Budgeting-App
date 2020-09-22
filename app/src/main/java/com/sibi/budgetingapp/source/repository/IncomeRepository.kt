package com.sibi.budgetingapp.source.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.db.IncomeDao
import com.sibi.budgetingapp.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val TAG = "IncomeRepositoy";

class IncomeRepository (val incomeDao: IncomeDao) {

    private val dispose: CompositeDisposable = CompositeDisposable()
    val dataIncome = MutableLiveData<HashMap<String, ArrayList<Income>>>()
    private var totalIncome = 0;
    val dataTotalIncome = MutableLiveData<Int>()
    val dataIncomeThisMonth = MutableLiveData<Pair<Int,Int>>()
    private var totalThisMonth = 0

    init {
        getData()

    }


    fun getData() {

        var month =Calendar.getInstance().get(Calendar.MONTH)+1
        dispose.add(
            incomeDao.getAll().subscribeOn(Schedulers.computation()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ incomes ->
                val mapIncome = HashMap<String, ArrayList<Income>>()
                val listHeader = ArrayList<String>()
                if(incomes.isEmpty()) {
                    totalIncome = 0
                }
                incomes.map { income ->
                    println("cek month ${month} ${income.date.split("/")[1]}")
                    if(month.toString() == income.date.split("/")[1]) {
                        totalThisMonth+=income.amount
                    }
                    totalIncome += income.amount
                    val key = formatDate(income.date)
                    if (mapIncome.containsKey(key)) {
                        mapIncome[key]!!.add(income);
                        mapIncome.put(key, mapIncome[key]!!)
                    } else {
                        val arrayIncome = ArrayList<Income>()
                        arrayIncome.add(income)
                        mapIncome.put(key, arrayIncome)
                    }
                }
                dataTotalIncome.postValue(totalIncome)
                dataIncome.postValue(mapIncome)
                dataIncomeThisMonth.postValue(Pair(month,totalThisMonth))
                totalThisMonth = 0
            }, {
                println("throwww")
            })
        )
    }

    fun insertData(income: Income) {
        dispose.add(
            incomeDao.insertIncome(income).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                Log.d(TAG, "success insert Data")
            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun deleteData(income: Income) {
        dispose.add(
            incomeDao.deleteIncome(income).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                Log.d(TAG, "success delete data")
            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun updateData(income: Income) {
        Log.d(
            TAG,
            "success update Data ${income.id} ${income.title} ${income.amount} ${income.date}"
        )

        dispose.add(
            incomeDao.updateIncome(income).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                Log.d(TAG, "success update Data")
            }, {
                Log.e(TAG, "error $it")
            })
        )
    }

    fun dispose() {
        dispose.clear()
    }

}