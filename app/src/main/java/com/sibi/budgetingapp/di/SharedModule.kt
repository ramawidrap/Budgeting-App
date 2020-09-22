package com.sibi.budgetingapp.di

import com.sibi.budgetingapp.di.viewmodel_module.ExpenseViewModelModule
import com.sibi.budgetingapp.di.viewmodel_module.IncomeViewModelModule
import com.sibi.budgetingapp.ui.expense.ExpenseEditActivity
import com.sibi.budgetingapp.ui.expense.ExpenseFragment
import com.sibi.budgetingapp.ui.income.IncomeEditActivity
import com.sibi.budgetingapp.ui.income.IncomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SharedModule {
    @ContributesAndroidInjector()
    abstract fun contributeIncomeFragment() : IncomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeExpenseFragment() : ExpenseFragment

    @ContributesAndroidInjector()
    abstract fun contributeEditActivity() : IncomeEditActivity

    @ContributesAndroidInjector()
    abstract fun contributeExpenseEditActivity() : ExpenseEditActivity

}