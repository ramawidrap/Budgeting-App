package com.sibi.budgetingapp.di

import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.source.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory (viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory


}