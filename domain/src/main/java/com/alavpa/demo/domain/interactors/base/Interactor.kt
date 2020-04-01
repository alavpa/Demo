package com.alavpa.demo.domain.interactors.base

import io.reactivex.Single

abstract class Interactor<T> {
    abstract fun build(): Single<T>
}
