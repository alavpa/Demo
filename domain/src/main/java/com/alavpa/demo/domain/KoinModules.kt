package com.alavpa.demo.domain

import com.alavpa.demo.domain.interactors.GetComments
import com.alavpa.demo.domain.interactors.GetPostDetail
import com.alavpa.demo.domain.interactors.GetPosts
import com.alavpa.demo.domain.interactors.GetUser
import org.koin.dsl.module

val domainModule = module {
    factory { GetPosts(dbDataSource = get(), apiDataSource = get()) }
    factory { GetPostDetail(dbDataSource = get(), apiDataSource = get(), getUser = get()) }
    factory { GetUser(dbDataSource = get(), apiDataSource = get()) }
    factory { GetComments(dbDataSource = get(), apiDataSource = get()) }
}
