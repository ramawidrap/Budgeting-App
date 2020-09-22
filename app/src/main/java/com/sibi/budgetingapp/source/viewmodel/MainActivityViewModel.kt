package com.sibi.budgetingapp.source.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.repository.ExpenseRepository
import com.sibi.budgetingapp.source.repository.IncomeRepository
import com.sibi.budgetingapp.source.repository.TypeUpdate
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {


    fun getTotalIncome(): LiveData<Int> {
        return incomeRepository.dataTotalIncome
    }

    fun getTotalExpense(): LiveData<Int> {
        return expenseRepository.dataTotalExpense
    }


    fun getUpdatedDataExpense(): LiveData<TypeUpdate> {
        return expenseRepository.dataUpdated
    }

    fun getAllTypeExpense(): LiveData<HashMap<String, Int>> {
        return expenseRepository.dataTotalExpenseAllType
    }


    fun getDataExpense(): LiveData<HashMap<String, ArrayList<Expense>>> {
        return expenseRepository.dataExpense;
    }

    fun getDataIncomeThisMonth() : LiveData<Pair<Int,Int>> {
        return incomeRepository.dataIncomeThisMonth
    }

    fun getDataExpenseThisMonth() : LiveData<Pair<Int,Int>> {
        return expenseRepository.dataTotalThisMonth
    }



    fun insertDataExpense(expense: Expense) {
        expenseRepository.insertData(expense)
    }

    fun deleteDataExpense(expense: Expense) {
        expenseRepository.deleteData(expense)
    }

    fun updateDataExpense(expense: Expense) {
        expenseRepository.updateData(expense);
    }


    fun dispose() {
        incomeRepository.dispose()
        expenseRepository.dispose()
    }

    fun getDataIncome(): LiveData<HashMap<String, ArrayList<Income>>> {
        return incomeRepository.dataIncome;
    }

    fun insertDataIncome(income: Income) {
        incomeRepository.insertData(income)
    }

    fun deleteDataIncome(income: Income) {
        incomeRepository.deleteData(income)
    }

    fun updateDataIncome(income: Income) {
        incomeRepository.updateData(income);
    }

}