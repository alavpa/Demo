package com.alavpa.demo.presentation.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ThreadProvider {
    fun provideMain(): Scheduler

    fun provideIO(): Scheduler
}

class ThreadProviderImpl : ThreadProvider {
    override fun provideMain(): Scheduler = AndroidSchedulers.mainThread()

    override fun provideIO(): Scheduler = Schedulers.io()
}
