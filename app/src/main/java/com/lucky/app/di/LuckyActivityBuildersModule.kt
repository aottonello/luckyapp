package com.lucky.app.di

import com.lucky.app.ui.LuckyMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

//Activities, dependency of activities are provided by this module
@Module
abstract class LuckyActivityBuildersModule {

    @ContributesAndroidInjector(modules = [LuckyViewModelModule::class, LuckyFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): LuckyMainActivity

}