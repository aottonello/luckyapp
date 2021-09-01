package com.lucky.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.repositories.LuckyRepository
import com.lucky.app.repositories.LuckyRepositoryImpl
import com.lucky.app.services.LuckyService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class LuckyRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var service: LuckyService

    private lateinit var repository: LuckyRepository

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        repository = LuckyRepositoryImpl(service)
    }

    @After
    open fun doClear() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `verify fetch service response successfully`() {
        val mockModel = mock<LuckyHomeModel>()

        whenever(service.getLuckyHomeSections())
            .thenReturn(Single.just(mockModel))

        repository.getLuckyHomeSections().test()
            .assertValue(mockModel)
            .assertComplete()

        verify(service).getLuckyHomeSections()
    }

    @Test
    fun `verify error when try to fetch service`() {
        val mockModel = Throwable()
        whenever(service.getLuckyHomeSections())
            .thenReturn(Single.error(mockModel))

        repository.getLuckyHomeSections().test()
            .onError(mockModel)

        verify(service).getLuckyHomeSections()
    }
}