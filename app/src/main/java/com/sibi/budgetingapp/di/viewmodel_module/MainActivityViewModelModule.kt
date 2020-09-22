package com.sibi.budgetingapp.di.viewmodel_module

import androidx.lifecycle.ViewModel
import com.sibi.budgetingapp.di.ViewModelKey
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityVMModule(mainActivityViewModel: MainActivityViewModel) : ViewModel
}