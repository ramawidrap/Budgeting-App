package com.sibi.budgetingapp.di

import com.sibi.budgetingapp.di.viewmodel_module.DetailBudgetViewModelModule
import com.sibi.budgetingapp.ui.MainActivity
import com.sibi.budgetingapp.di.viewmodel_module.ExpenseViewModelModule
import com.sibi.budgetingapp.di.viewmodel_module.IncomeViewModelModule
import com.sibi.budgetingapp.di.viewmodel_module.MainActivityViewModelModule
import com.sibi.budgetingapp.ui.DetailBudgetActivity
import com.sibi.budgetingapp.ui.expense.ExpenseEditActivity
import com.sibi.budgetingapp.ui.income.IncomeEditActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class,MainActivityViewModelModule::class])
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [IncomeViewModelModule::class])
    abstract fun contributeEditActivity() : IncomeEditActivity

    @ContributesAndroidInjector(modules = [ExpenseViewModelModule::class])
    abstract fun contributeExpenseEditActivity() : ExpenseEditActivity

    @ContributesAndroidInjector(modules = [DetailBudgetViewModelModule::class])
    abstract fun contributeDetailBudgetActivity() : DetailBudgetActivity
}