package com.sibi.budgetingapp.di

import com.sibi.budgetingapp.di.viewmodel_module.ExpenseViewModelModule
import com.sibi.budgetingapp.di.viewmodel_module.IncomeViewModelModule
import com.sibi.budgetingapp.ui.expense.ExpenseFragment
import com.sibi.budgetingapp.ui.income.IncomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = [IncomeViewModelModule::class])
    abstract fun contributeIncomeFragment() : IncomeFragment

    @ContributesAndroidInjector(modules = [ExpenseViewModelModule::class])
    abstract fun contributeExpenseFragment() : ExpenseFragment

}