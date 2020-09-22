package com.sibi.budgetingapp.di.viewmodel_module

import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.di.ViewModelKey
import com.sibi.budgetingapp.source.viewmodel.DetailBudgetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailBudgetViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailBudgetViewModel::class)
    abstract fun bindDetailBudgetViewModel(detailBudgetViewModel: DetailBudgetViewModel) : ViewModel
}