package com.alavpa.demo

import com.alavpa.demo.android.detail.PostDetailNavigationImpl
import com.alavpa.demo.android.detail.writer.CoUKWriter
import com.alavpa.demo.android.detail.writer.InfoWriter
import com.alavpa.demo.android.detail.writer.WriterManager
import com.alavpa.demo.android.main.MainNavigationImpl
import com.alavpa.demo.android.user.UserNavigationImpl
import org.koin.dsl.module

val androidModule = module {
    factory { MainNavigationImpl() }
    factory { PostDetailNavigationImpl() }
    factory { UserNavigationImpl() }
    single { WriterManager(listOf(CoUKWriter(), InfoWriter())) }
}
