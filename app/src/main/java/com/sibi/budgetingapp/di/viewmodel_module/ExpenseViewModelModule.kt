package com.sibi.budgetingapp.di.viewmodel_module

import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.di.ViewModelKey
import com.sibi.budgetingapp.source.viewmodel.ExpenseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ExpenseViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExpenseViewModel::class)
    abstract fun bindExpenseViewModel(expenseViewModel: ExpenseViewModel) : ViewModel
}