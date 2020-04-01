package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.ItemNotFoundException
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.interactors.base.Interactor
import com.alavpa.demo.domain.model.User
import io.reactivex.Single

class GetUser(private val dbDataSource: DbDataSource, private val apiDataSource: ApiDataSource) : Interactor<User>() {
    var id: Long = 0
    override fun build(): Single<User> {
        return apiDataSource.getUser(id)
            .flatMap { dbDataSource.insertUser(it).andThen(dbDataSource.getUser(id).switchIfEmpty(Single.error(ItemNotFoundException()))) }
            .onErrorResumeNext {
            if (it is NoInternetException) {
                dbDataSource.getUser(id).switchIfEmpty(Single.error(ItemNotFoundException()))
            } else Single.error(it)
        }
    }
}
