package com.alavpa.demo.data.database

import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class DbDataSourceImpl(private val db: DemoDb) : DbDataSource {
    override fun getPosts(start: Int, limit: Int): Single<List<Post>> {
        return db.postDao().get(start, limit).map { it.map { postEntity -> postEntity.toPost() } }
    }

    override fun getComments(postId: Long): Single<List<Comment>> {
        return db.commentDao().get(postId).map { it.map { commentEntity -> commentEntity.toComment() } }
    }

    override fun insertPosts(posts: List<Post>): Completable {
        return db.postDao().upsertAll(posts.map { it.toEntity() })
    }

    override fun insertPost(post: Post): Completable {
        return db.postDao().upsert(post.toEntity())
    }

    override fun insertUser(user: User): Completable {
        return db.userDao().upsert(user.toEntity())
    }

    override fun insertComments(comments: List<Comment>): Completable {
        return db.commentDao().upsertAll(comments.map { it.toEntity() })
    }

    override fun getPost(id: Long): Maybe<Post> {
        return db.postDao().get(id).map { it.toPost() }
    }

    override fun getUser(id: Long): Maybe<User> {
        return db.userDao().get(id).map { it.toUser() }
    }
}
