package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.ItemNotFoundException
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.interactors.base.Interactor
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.PostDetail
import com.alavpa.demo.domain.model.User
import io.reactivex.Single

class GetPostDetail(private val dbDataSource: DbDataSource, private val apiDataSource: ApiDataSource, private val getUser: GetUser) : Interactor<PostDetail>() {
    var id: Long = 0
    override fun build(): Single<PostDetail> {
        return apiDataSource.getPost(id)
            .flatMap { dbDataSource.insertPost(it).andThen(dbDataSource.getPost(id).switchIfEmpty(Single.error(ItemNotFoundException()))) }
            .onErrorResumeNext { exception ->
                if (exception is NoInternetException) {
                    dbDataSource.getPost(id).switchIfEmpty(Single.error(ItemNotFoundException()))
                } else Single.error(exception)
            }.flatMap { post ->
                getUser.id = post.userId
                getUser.build().map { user -> post.toPostDetail(user) }
            }
    }

    private fun Post.toPostDetail(user: User): PostDetail {
        return PostDetail(
            this.id,
            this.title,
            this.body,
            user
        )
    }
}
