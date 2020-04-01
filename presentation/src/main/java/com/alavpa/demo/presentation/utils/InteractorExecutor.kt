package com.alavpa.demo.presentation.utils

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface InteractorExecutor {
    fun <T> exec(single: Single<T>, onSuccess: (T) -> Unit, onException: (Throwable) -> Unit = { Timber.e(it) })
    fun clear()
}

class InteractorExecutorImpl(private val threadProvider: ThreadProvider, private val compositeDisposable: CompositeDisposable) : InteractorExecutor {

    override fun <T> exec(single: Single<T>, onSuccess: (T) -> Unit, onException: (Throwable) -> Unit) {
        single.subscribeOn(threadProvider.provideIO()).observeOn(threadProvider.provideMain()).subscribe(onSuccess, onException).also {
            compositeDisposable.add(it)
        }
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}
