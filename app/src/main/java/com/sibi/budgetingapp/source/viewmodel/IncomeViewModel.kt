package com.sibi.budgetingapp.source.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.repository.IncomeRepository
import javax.inject.Inject

class IncomeViewModel @Inject constructor(val incomeRepository: IncomeRepository) : ViewModel() {
    fun getData() : LiveData<HashMap<String,ArrayList<Income>>> {
        return incomeRepository.dataIncome;
    }

    fun getTotal() : LiveData<Int> {
        return incomeRepository.dataTotalIncome
    }

    fun insertData(income : Income) {
        incomeRepository.insertData(income)
    }

    fun deleteData(income: Income) {
        incomeRepository.deleteData(income)
    }

    fun updateData(income: Income) {
        incomeRepository.updateData(income);
    }

    fun dispose() {
        incomeRepository.dispose()
    }
}