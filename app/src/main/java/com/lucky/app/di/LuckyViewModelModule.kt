package com.lucky.app.di

import androidx.lifecycle.ViewModel
import com.lucky.app.viewmodels.LuckyHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class LuckyViewModelModule {

    @Binds
    @IntoMap
    @LuckyViewModelKey(LuckyHomeViewModel::class)
    abstract fun bindMainViewModel(luckyViewModel: LuckyHomeViewModel): ViewModel
}
