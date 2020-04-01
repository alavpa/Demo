package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.interactors.base.Interactor
import com.alavpa.demo.domain.model.Comment
import io.reactivex.Single

class GetComments(
    private val dbDataSource: DbDataSource,
    private val apiDataSource: ApiDataSource
) : Interactor<List<Comment>>() {

    var id: Long = 0
    override fun build(): Single<List<Comment>> {
        return apiDataSource.getComments(id).flatMap {
            dbDataSource.insertComments(it).andThen(getDbComments())
        }.onErrorResumeNext {
            if (it is NoInternetException) getDbComments() else Single.error(it)
        }
    }

    private fun getDbComments(): Single<List<Comment>> {
        return dbDataSource.getComments(id)
    }
}
