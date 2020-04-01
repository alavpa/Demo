package com.alavpa.demo.domain.datasource

import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface DbDataSource {
    fun getPosts(start: Int, limit: Int): Single<List<Post>>
    fun getPost(id: Long): Maybe<Post>
    fun getUser(id: Long): Maybe<User>
    fun getComments(postId: Long): Single<List<Comment>>
    fun insertPosts(posts: List<Post>): Completable
    fun insertPost(post: Post): Completable
    fun insertUser(user: User): Completable
    fun insertComments(comments: List<Comment>): Completable
}
