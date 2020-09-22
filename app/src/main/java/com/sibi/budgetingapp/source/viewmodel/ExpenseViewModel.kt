package com.sibi.budgetingapp.source.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.repository.ExpenseRepository
import javax.inject.Inject

class ExpenseViewModel @Inject constructor(val expenseRepository: ExpenseRepository) : ViewModel() {


}