package com.lucky.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucky.app.R
import com.lucky.app.actions.LuckyActions
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemDetailModel
import com.lucky.app.repositories.LuckyRepository
import com.lucky.app.utils.LuckySingleLiveEvent
import com.lucky.app.utils.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Fetch data from Repository and update Views
 */
class LuckyHomeViewModel @Inject constructor(
    private val luckyRepository: LuckyRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val _showProgress = LuckySingleLiveEvent<Boolean>()
    val showProgress: LiveData<Boolean> get() = _showProgress

    private val _showErrorMessage = LuckySingleLiveEvent<String>()
    val showErrorMessage: LiveData<String> get() = _showErrorMessage

    private val _uiModelActions = MutableLiveData<LuckyActions>()
    val uiActionModel: LiveData<LuckyActions> get() = _uiModelActions

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun fetchHomeSections() {
        disposables.add(
            luckyRepository.getLuckyHomeSections()
                .doOnSubscribe { showProgress(true) }
                .doFinally { showProgress(false) }
                .applySchedulers()
                .subscribe(
                    this::updateSectionsHome,
                    this::onLoadError
                )
        )
    }

    fun fetchDetailItem( context: Context) {
        disposables.add(
            luckyRepository.getDetailItem(context)
                .doOnSubscribe { showProgress(true) }
                .doFinally { showProgress(false) }
                .applySchedulers()
                .subscribe(
                    this::updateDetailItem,
                    this::onLoadError
                )
        )
    }

    private fun updateSectionsHome(sectionsModel: LuckyHomeModel) {
        updateModel(LuckyActions.SectionsHome(sectionsModel))
    }

    private fun updateDetailItem(sectionsModel: LuckyItemDetailModel) {
        updateModel(LuckyActions.ItemDetail(sectionsModel))
    }

    private fun onLoadError(error: Throwable) {
        showError(R.string.copy_error_loading_data.toString())
    }

    fun updateModel(uiModel: LuckyActions) {
        _uiModelActions.postValue(uiModel)
    }

    fun showProgress(show: Boolean) {
        _showProgress.postValue(show)
    }

    fun showError(errorMessage: String) {
        _showErrorMessage.postValue(errorMessage)
    }
}