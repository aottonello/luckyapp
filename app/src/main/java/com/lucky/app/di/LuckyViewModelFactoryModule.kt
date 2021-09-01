package com.lucky.app.di

import androidx.lifecycle.ViewModelProvider
import com.lucky.app.repositories.LuckyRepository
import com.lucky.app.repositories.LuckyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable


@Module
abstract class LuckyViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(luckyViewModelProvideFactory: LuckyViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @Reusable
    abstract fun bindLuckyRepository(repository: LuckyRepositoryImpl): LuckyRepository
}