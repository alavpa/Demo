package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.exceptions.ServerException
import com.alavpa.demo.domain.model.Post
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetPostsTest {
    private lateinit var interactor: GetPosts
    private val apiDataSource: ApiDataSource = mock()
    private val dbDataSource: DbDataSource = mock()

    @Before
    fun setup() {
        interactor = GetPosts(dbDataSource, apiDataSource)
    }

    @Test
    fun getPostsHappyPath() {
        given(apiDataSource.getPosts()).willReturn(Single.just(listOf(Post())))
        given(dbDataSource.insertPosts(any())).willReturn(Completable.complete())
        given(dbDataSource.getPosts(any(), any())).willReturn(Single.just(listOf(Post())))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Post()))
        }

        verify(apiDataSource).getPosts()
        verify(dbDataSource).insertPosts(any())
        verify(dbDataSource).getPosts(any(), any())
    }

    @Test
    fun getPostsNoInternetError() {
        given(apiDataSource.getPosts()).willReturn(Single.error(NoInternetException()))
        given(dbDataSource.insertPosts(any())).willReturn(Completable.complete())
        given(dbDataSource.getPosts(any(), any())).willReturn(Single.just(listOf(Post())))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Post()))
        }

        verify(apiDataSource).getPosts()
        verify(dbDataSource).getPosts(any(), any())
    }

    @Test
    fun getPostsServerException() {
        given(apiDataSource.getPosts()).willReturn(Single.error(ServerException()))
        given(dbDataSource.insertPosts(any())).willReturn(Completable.complete())
        given(dbDataSource.getPosts(any(), any())).willReturn(Single.just(listOf(Post())))
        interactor.build().test().also {
            it.assertError(ServerException::class.java)
            it.assertNoValues()
        }
    }
}
