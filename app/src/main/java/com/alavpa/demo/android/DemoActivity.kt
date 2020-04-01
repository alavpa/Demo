package com.alavpa.demo.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alavpa.demo.presentation.Presenter

abstract class DemoActivity<T> : AppCompatActivity() {

    private var basePresenter: Presenter<T>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.basePresenter = presenter()
        navigation().attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        basePresenter?.onDestroy()
        navigation().detach()
    }

    abstract fun presenter(): Presenter<T>
    abstract fun navigation(): DemoNavigation
}
