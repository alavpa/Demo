package com.alavpa.demo.presentation

import com.alavpa.demo.presentation.utils.InteractorExecutor
import com.alavpa.demo.presentation.utils.InteractorExecutorImpl
import com.alavpa.demo.presentation.utils.ThreadProvider
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val testModule = module {
    single(override = true) { threadProvider() }
    single<InteractorExecutor>(override = true) { InteractorExecutorImpl(get(), CompositeDisposable()) }
}

fun threadProvider(): ThreadProvider {
    return mock<ThreadProvider>().also {
        given(it.provideIO()).willReturn(Schedulers.trampoline())
        given(it.provideMain()).willReturn(Schedulers.trampoline())
    }
}
