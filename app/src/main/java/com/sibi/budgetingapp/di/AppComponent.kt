package com.sibi.budgetingapp.di

import android.app.Application
import com.sibi.budgetingapp.App
import com.sibi.budgetingapp.di.viewmodel_module.DetailBudgetViewModelModule
import com.sibi.budgetingapp.di.viewmodel_module.MainActivityViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ViewModelFactoryModule::class, ActivityBuilderModule::class,
    MainActivityViewModelModule::class,DetailBudgetViewModelModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun getApp(app: Application): Builder

        fun build(): AppComponent
    }
}