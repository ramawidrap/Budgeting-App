package com.sibi.budgetingapp.di

import com.sibi.budgetingapp.MainActivity
import com.sibi.budgetingapp.di.viewmodel_module.IncomeViewModelModule
import com.sibi.budgetingapp.ui.EditActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [IncomeViewModelModule::class])
    abstract fun contributeEditActivity() : EditActivity
}