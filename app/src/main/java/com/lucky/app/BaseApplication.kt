package com.lucky.app

import com.lucky.app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * BaseApplication using DaggerApplication
 */
class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
