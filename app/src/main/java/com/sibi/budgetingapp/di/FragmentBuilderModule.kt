package com.sibi.budgetingapp.di

import androidx.fragment.app.Fragment
import com.sibi.budgetingapp.di.viewmodel_module.IncomeViewModelModule
import com.sibi.budgetingapp.source.IncomeViewModel
import com.sibi.budgetingapp.ui.ExpenseFragment
import com.sibi.budgetingapp.ui.IncomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = [IncomeViewModelModule::class])
    abstract fun contributeIncomeFragment() : IncomeFragment

}