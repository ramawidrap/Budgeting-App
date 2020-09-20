package com.sibi.budgetingapp.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.db.IncomeDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "IncomeRepositoy";

class IncomeRepository @Inject constructor(val incomeDao: IncomeDao) {

    private val dispose: CompositeDisposable = CompositeDisposable()
    val dataIncome = MutableLiveData<HashMap<String, ArrayList<Income>>>()

    init {
        getData()
    }

    fun getData() {
        dispose.add(
            incomeDao.getAll().subscribeOn(Schedulers.computation()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ incomes ->
                print("cek update")
                val mapIncome = HashMap<String, ArrayList<Income>>()
                val listHeader = ArrayList<String>()
                incomes.map { income ->
                    if (mapIncome.containsKey(income.date)) {
                        mapIncome[income.date]!!.add(income);
                        mapIncome.put(income.date, mapIncome[income.date]!!)
                    } else {
                        val arrayIncome = ArrayList<Income>()
                        arrayIncome.add(income)
                        mapIncome.put(income.date, arrayIncome)
                    }
                }
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