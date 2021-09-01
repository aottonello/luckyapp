package com.lucky.app

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lucky.app.actions.LuckyActions
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemDetailModel
import com.lucky.app.repositories.LuckyRepository
import com.lucky.app.viewmodels.LuckyHomeViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * Verify then main methods from viewModel
 */
class LuckyHomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var mockErrorMessages: Observer<String>

    @Mock
    private lateinit var mockActions: Observer<LuckyActions>

    @Mock
    private lateinit var repository: LuckyRepository

    private lateinit var viewModel: LuckyHomeViewModel

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = LuckyHomeViewModel(repository).apply {
            uiActionModel.observeForever(mockActions)
            showErrorMessage.observeForever(mockErrorMessages)
        }
    }

    @After
    open fun doClear() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `verify success load data from service`() {
        val mockModel = mock <LuckyHomeModel>()
        whenever(repository.getLuckyHomeSections()).thenReturn(Single.just(mockModel))

        viewModel.fetchHomeSections()

        verify(mockActions).onChanged(
            refEq(LuckyActions.SectionsHome(mockModel))
        )
        assertNull(viewModel.showErrorMessage.value)
    }

    @Test
    fun `verify exception error when load data from service`() {
        val testErrorMessage =  "2131820579"
        whenever(repository.getLuckyHomeSections()).thenReturn(Single.error(Throwable()))
        viewModel.fetchHomeSections()

        verify(mockErrorMessages).onChanged(
            eq(testErrorMessage)
        )
        assertNull(viewModel.uiActionModel.value)
    }


    @Test
    fun `verify success detail data from json`() {
        val mockDetailModel = mock <LuckyItemDetailModel>()
        whenever(repository.getDetailItem(context)).thenReturn(Single.just(mockDetailModel))

        viewModel.fetchDetailItem(context)

        verify(mockActions).onChanged(
            refEq(LuckyActions.ItemDetail(mockDetailModel))
        )
        assertNull(viewModel.showErrorMessage.value)
    }

    @Test
    fun `verify exception error when detail data from json`() {
        val testErrorMessage =  "2131820579"
        whenever(repository.getDetailItem(context)).thenReturn(Single.error(Throwable()))
        viewModel.fetchDetailItem(context)

        verify(mockErrorMessages).onChanged(
            eq(testErrorMessage)
        )
        assertNull(viewModel.uiActionModel.value)
    }
}