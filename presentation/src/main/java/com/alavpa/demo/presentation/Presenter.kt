package com.alavpa.demo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alavpa.demo.domain.interactors.base.Interactor
import com.alavpa.demo.presentation.utils.InteractorExecutor
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

abstract class Presenter<T> : KoinComponent, ViewModel() {
    private val interactorExecutor: InteractorExecutor by inject()
    private var renderLiveData = MutableLiveData<T>()
    fun getRenderLiveData(): LiveData<T> = renderLiveData

    val viewModel: T
        get() = renderLiveData.value ?: default()

    fun render(viewModel: T) {
        renderLiveData.value = viewModel
    }

    open fun onDestroy() {
        interactorExecutor.clear()
    }

    fun <S> Interactor<S>.exec(
        onError: (Throwable) -> Unit = { Timber.e(it) },
        onSuccess: (S) -> Unit
    ) {
        interactorExecutor.exec(this.build(), onSuccess, onError)
    }

    abstract fun default(): T
}
