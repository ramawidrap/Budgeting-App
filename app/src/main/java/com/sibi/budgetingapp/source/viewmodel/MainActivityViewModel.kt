package com.sibi.budgetingapp.source.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.repository.ExpenseRepository
import com.sibi.budgetingapp.source.repository.IncomeRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val incomeRepository: IncomeRepository, private val expenseRepository: ExpenseRepository) : ViewModel() {


    fun getTotalIncome() : LiveData<Int> {
        return incomeRepository.dataTotalIncome
    }

    fun getTotalExpense() : LiveData<Int> {
        return expenseRepository.dataTotalExpense
    }


    fun isShowNotif() : LiveData<Boolean> {
        return expenseRepository.dataShowNotif
    }

    fun getUpdatedData() : LiveData<Pair<String,Int>> {
        return expenseRepository.dataUpdated
    }

    fun getAllType() : LiveData<HashMap<String,Int>> {
        expenseRepository.getExpenseAllType()
        return expenseRepository.dataTotalExpenseAllType
    }

    fun getTotal() : LiveData<Int> {
        return  expenseRepository.dataTotalExpense
    }
    fun getData() : LiveData<HashMap<String, ArrayList<Expense>>> {
        return expenseRepository.dataExpense;
    }

    fun insertData(expense: Expense) {
        expenseRepository.insertData(expense)
    }

    fun deleteData(expense: Expense) {
        expenseRepository.deleteData(expense)
    }

    fun updateData(expense: Expense) {
        expenseRepository.updateData(expense);
    }


    fun dispose() {
        expenseRepository.dispose()
    }
}