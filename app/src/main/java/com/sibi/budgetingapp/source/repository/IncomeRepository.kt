package com.sibi.budgetingapp.source.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.db.IncomeDao
import com.sibi.budgetingapp.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "IncomeRepositoy";

class IncomeRepository (val incomeDao: IncomeDao) {

    private val dispose: CompositeDisposable = CompositeDisposable()
    val dataIncome = MutableLiveData<HashMap<String, ArrayList<Income>>>()
    private var totalIncome = 0;
    val dataTotalIncome = MutableLiveData<Int>()

    init {
        getData()

    }


    fun getData() {
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