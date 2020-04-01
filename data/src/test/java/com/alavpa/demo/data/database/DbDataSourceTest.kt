package com.alavpa.demo.data.database

import com.alavpa.demo.data.database.dao.CommentDao
import com.alavpa.demo.data.database.dao.PostDao
import com.alavpa.demo.data.database.dao.UserDao
import com.alavpa.demo.data.database.model.CommentEntity
import com.alavpa.demo.data.database.model.PostEntity
import com.alavpa.demo.data.database.model.UserEntity
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.IOException
import org.junit.Before
import org.junit.Test

class DbDataSourceTest {
    private lateinit var dbDatasource: DbDataSource
    private val db: DemoDb = mock()
    private val postDao: PostDao = mock()
    private val userDao: UserDao = mock()
    private val commentDao: CommentDao = mock()

    @Before
    fun setup() {
        dbDatasource = DbDataSourceImpl(db)
        given(db.postDao()).willReturn(postDao)
        given(db.userDao()).willReturn(userDao)
        given(db.commentDao()).willReturn(commentDao)
    }

    @Test
    fun getPostsHappyPathTest() {
        given(postDao.get(any(), any())).willReturn(Single.just(listOf(PostEntity(1))))
        dbDatasource.getPosts(0, 1).test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Post(1)))
        }
    }

    @Test
    fun getPostsExceptionTest() {
        given(postDao.get(any(), any())).willReturn(Single.error(IOException()))
        dbDatasource.getPosts(0, 1).test().also {
            it.assertError(IOException::class.java)
        }
    }

    @Test
    fun getPostHappyPathTest() {
        given(postDao.get(any())).willReturn(Maybe.just(PostEntity(1)))
        dbDatasource.getPost(5).test().also {
            it.assertNoErrors()
            it.assertValue(Post(1))
        }
    }

    @Test
    fun getPostNotFoundTest() {
        given(postDao.get(any())).willReturn(Maybe.empty())
        dbDatasource.getPost(5).test().also {
            it.assertNoErrors()
            it.assertNoValues()
            it.assertComplete()
        }
    }

    @Test
    fun getPostExceptionTest() {
        given(postDao.get(any())).willReturn(Maybe.error(IOException()))
        dbDatasource.getPost(5).test().also {
            it.assertError(IOException::class.java)
        }
    }

    @Test
    fun getUserHappyPathTest() {
        given(userDao.get(any())).willReturn(Maybe.just(UserEntity(1)))
        dbDatasource.getUser(5).test().also {
            it.assertNoErrors()
            it.assertValue(User(1))
        }
    }

    @Test
    fun getUserNotFoundTest() {
        given(userDao.get(any())).willReturn(Maybe.empty())
        dbDatasource.getUser(5).test().also {
            it.assertNoErrors()
            it.assertNoValues()
            it.assertComplete()
        }
    }

    @Test
    fun getUserExceptionTest() {
        given(userDao.get(any())).willReturn(Maybe.error(IOException()))
        dbDatasource.getUser(5).test().also {
            it.assertError(IOException::class.java)
        }
    }

    @Test
    fun getCommentsHappyPathTest() {
        given(commentDao.get(any())).willReturn(Single.just(listOf(CommentEntity(1))))
        dbDatasource.getComments(5).test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Comment(1)))
        }
    }

    @Test
    fun getCommentsExceptionTest() {
        given(commentDao.get(any())).willReturn(Single.error(IOException()))
        dbDatasource.getComments(5).test().also {
            it.assertError(IOException::class.java)
        }
    }

    @Test
    fun insertPostsHappyPathTest() {
        given(postDao.upsertAll(any())).willReturn(Completable.complete())
        dbDatasource.insertPosts(listOf(Post(1))).test().also {
            it.assertNoErrors()
            it.assertComplete()
        }
    }

    @Test
    fun insertPostsExceptionTest() {
        given(postDao.upsertAll(any())).willReturn(Completable.error(IOException()))
        dbDatasource.insertPosts(listOf(Post(1))).test().also {
            it.assertError(IOException::class.java)
            it.assertNotComplete()
        }
    }

    @Test
    fun insertPostHappyPathTest() {
        given(postDao.upsert(any())).willReturn(Completable.complete())
        dbDatasource.insertPost(Post(1)).test().also {
            it.assertNoErrors()
            it.assertComplete()
        }
    }

    @Test
    fun insertPostExceptionTest() {
        given(postDao.upsert(any())).willReturn(Completable.error(IOException()))
        dbDatasource.insertPost(Post(1)).test().also {
            it.assertError(IOException::class.java)
            it.assertNotComplete()
        }
    }

    @Test
    fun insertUserHappyPathTest() {
        given(userDao.upsert(any())).willReturn(Completable.complete())
        dbDatasource.insertUser(User(1)).test().also {
            it.assertNoErrors()
            it.assertComplete()
        }
    }

    @Test
    fun insertUserExceptionTest() {
        given(userDao.upsert(any())).willReturn(Completable.error(IOException()))
        dbDatasource.insertUser(User(1)).test().also {
            it.assertError(IOException::class.java)
            it.assertNotComplete()
        }
    }

    @Test
    fun insertCommentsHappyPathTest() {
        given(commentDao.upsertAll(any())).willReturn(Completable.complete())
        dbDatasource.insertComments(listOf(Comment(1))).test().also {
            it.assertNoErrors()
            it.assertComplete()
        }
    }

    @Test
    fun insertCommentsExceptionTest() {
        given(commentDao.upsertAll(any())).willReturn(Completable.error(IOException()))
        dbDatasource.insertComments(listOf(Comment(1))).test().also {
            it.assertError(IOException::class.java)
            it.assertNotComplete()
        }
    }
}
