package com.sibi.budgetingapp.di

import com.sibi.budgetingapp.di.viewmodel_module.DetailBudgetViewModelModule
import com.sibi.budgetingapp.ui.MainActivity
import com.sibi.budgetingapp.di.viewmodel_module.MainActivityViewModelModule
import com.sibi.budgetingapp.ui.DetailBudgetActivity
import com.sibi.budgetingapp.ui.expense.ExpenseEditActivity
import com.sibi.budgetingapp.ui.income.IncomeEditActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [SharedModule::class,MainActivityViewModelModule::class])
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [MainActivityViewModelModule::class])
    abstract fun contributeIncomeEditActivity() : IncomeEditActivity

    @ContributesAndroidInjector(modules = [MainActivityViewModelModule::class])
    abstract fun contributeExpenseEditActivity() : ExpenseEditActivity

    @ContributesAndroidInjector(modules = [DetailBudgetViewModelModule::class])
    abstract fun contributeDetailBudgetActivity() : DetailBudgetActivity
}