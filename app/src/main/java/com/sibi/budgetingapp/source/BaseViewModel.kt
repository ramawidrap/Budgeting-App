package com.sibi.budgetingapp.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Income

abstract class BaseViewModel() : ViewModel() {
    abstract fun getData() : LiveData<HashMap<String, ArrayList<Income>>>
    abstract fun insertData(income : Income)

    abstract fun deleteData(income: Income)

    abstract fun updateData(income: Income)

    fun dispose() {
        incomeRepository.dispose()
    }
}