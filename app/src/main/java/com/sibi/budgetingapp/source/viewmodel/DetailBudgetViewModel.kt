package com.sibi.budgetingapp.source.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.source.repository.ExpenseRepository
import javax.inject.Inject

class DetailBudgetViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel() {
    fun getExpenseByType(type:String) : LiveData<Int> {
        expenseRepository.getExpenseByType(type)
        return expenseRepository.dataTotalExpenseByType
    }

    fun getExpenseAllType() : LiveData<HashMap<String,Int>>{
        expenseRepository.getExpenseAllType()
        return expenseRepository.dataTotalExpenseAllType
    }
}