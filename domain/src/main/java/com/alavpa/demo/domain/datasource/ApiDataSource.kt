package com.alavpa.demo.domain.datasource

import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import io.reactivex.Single

interface ApiDataSource {
    fun getPosts(): Single<List<Post>>
    fun getPost(id: Long): Single<Post>
    fun getUser(id: Long): Single<User>
    fun getComments(postId: Long): Single<List<Comment>>
}
