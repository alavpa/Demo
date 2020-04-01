package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.interactors.base.Interactor
import com.alavpa.demo.domain.model.Post
import io.reactivex.Single

class GetPosts(private val dbDataSource: DbDataSource, private val apiDataSource: ApiDataSource) : Interactor<List<Post>>() {
    companion object {
        private const val PAGE_SIZE = 5
    }

    var page = 0
    override fun build(): Single<List<Post>> {
        return if (page == 0) apiDataSource.getPosts().flatMap {
            dbDataSource.insertPosts(it).andThen(getDbPosts())
        }.onErrorResumeNext {
            if (it is NoInternetException) getDbPosts() else Single.error(it)
        } else getDbPosts()
    }

    private fun getDbPosts(): Single<List<Post>> {
        return dbDataSource.getPosts(page * PAGE_SIZE, PAGE_SIZE)
    }
}
