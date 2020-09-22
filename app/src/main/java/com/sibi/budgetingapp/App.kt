package com.sibi.budgetingapp

import com.facebook.stetho.Stetho
import com.sibi.budgetingapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Stetho.initializeWithDefaults(this)
        return DaggerAppComponent.builder().getApp(this).build()
    }
}