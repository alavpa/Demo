package com.alavpa.demo.data.api

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.exceptions.ServerException
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import io.reactivex.Single
import retrofit2.Response

class ApiDataSourceImpl(private val apiClient: ApiClient) : ApiDataSource {
    override fun getPosts(): Single<List<Post>> {
        return apiClient.posts().process().map { it.map { postResponse -> postResponse.toPost() } }
    }

    override fun getPost(id: Long): Single<Post> {
        return apiClient.post(id).process().map { it.toPost() }
    }

    override fun getUser(id: Long): Single<User> {
        return apiClient.user(id).process().map { it.toUser() }
    }

    override fun getComments(postId: Long): Single<List<Comment>> {
        return apiClient.comments(postId).process().map { it.map { commentResponse -> commentResponse.toComment() } }
    }

    private fun <T> Single<Response<T>>.process(): Single<T> {
        return this.onErrorResumeNext { Single.error(NoInternetException()) }.map {
            if (it.isSuccessful) it.body() ?: throw ServerException()
            else throw ServerException()
        }
    }
}
