package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.CommentResponse
import com.alavpa.demo.data.api.model.PostResponse
import com.alavpa.demo.data.api.model.UserResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    fun posts(): Single<Response<List<PostResponse>>>

    @GET("posts/{id}")
    fun post(@Path("id") id: Long): Single<Response<PostResponse>>

    @GET("users/{id}")
    fun user(@Path("id") id: Long): Single<Response<UserResponse>>

    @GET("comments")
    fun comments(@Query("postId") id: Long): Single<Response<List<CommentResponse>>>
}
