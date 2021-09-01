package com.lucky.app.di

import android.app.Application
import com.lucky.app.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Create Component
 * Include all modules with app will work
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        LuckyActivityBuildersModule::class,
        LuckyNetworkModule::class,
        LuckyViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    // @Component.Builder create component.
    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}