package com.lucky.app.di

import com.lucky.app.services.LuckyService
import com.lucky.app.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
object LuckyNetworkModule {
    /**
     * Provides the LuckyService service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the LuckyService service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLuckyApi(retrofit: Retrofit): LuckyService {
        return retrofit.create(LuckyService::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}