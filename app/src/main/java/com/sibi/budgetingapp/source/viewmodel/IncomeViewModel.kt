package com.sibi.budgetingapp.source.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.repository.IncomeRepository
import javax.inject.Inject

class IncomeViewModel @Inject constructor(val incomeRepository: IncomeRepository) : ViewModel() {

}