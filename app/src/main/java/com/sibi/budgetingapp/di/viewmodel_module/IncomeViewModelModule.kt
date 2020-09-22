package com.sibi.budgetingapp.di.viewmodel_module

import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.di.ViewModelKey
import com.sibi.budgetingapp.source.viewmodel.IncomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class IncomeViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(IncomeViewModel::class)
    abstract fun bindIncomeViewModel(incomeViewModel: IncomeViewModel) : ViewModel
}