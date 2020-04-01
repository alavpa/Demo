package com.alavpa.demo.presentation

import com.alavpa.demo.presentation.detail.PostDetailPresenter
import com.alavpa.demo.presentation.main.MainPresenter
import com.alavpa.demo.presentation.user.UserPresenter
import com.alavpa.demo.presentation.utils.InteractorExecutor
import com.alavpa.demo.presentation.utils.InteractorExecutorImpl
import com.alavpa.demo.presentation.utils.ThreadProvider
import com.alavpa.demo.presentation.utils.ThreadProviderImpl
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory<ThreadProvider> { ThreadProviderImpl() }
    factory { CompositeDisposable() }
    factory<InteractorExecutor> { InteractorExecutorImpl(threadProvider = get(), compositeDisposable = get()) }
    viewModel { MainPresenter(getPosts = get()) }
    viewModel { PostDetailPresenter(getPostDetail = get(), getComments = get()) }
    viewModel { UserPresenter(getUser = get()) }
}
