package com.lucky.app.di


import com.lucky.app.ui.LuckyDetailFragment
import com.lucky.app.ui.LuckySectionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

//Fragments, dependency of fragments are provided by this module
@Module
abstract class LuckyFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): LuckySectionsFragment

    @ContributesAndroidInjector
    abstract fun contributeEditFragment(): LuckyDetailFragment
}