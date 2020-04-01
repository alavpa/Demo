package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.exceptions.ServerException
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.PostDetail
import com.alavpa.demo.domain.model.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetPostDetailTest {
    private lateinit var interactor: GetPostDetail
    private val apiDataSource: ApiDataSource = mock()
    private val dbDataSource: DbDataSource = mock()
    private val getUser: GetUser = mock()

    @Before
    fun setup() {
        interactor = GetPostDetail(dbDataSource, apiDataSource, getUser)
    }

    @Test
    fun getPostDetailHappyPath() {
        given(apiDataSource.getPost(any())).willReturn(Single.just(Post()))
        given(dbDataSource.insertPost(any())).willReturn(Completable.complete())
        given(dbDataSource.getPost(any())).willReturn(Maybe.just(Post()))
        given(getUser.build()).willReturn(Single.just(User()))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(PostDetail())
        }

        verify(apiDataSource).getPost(any())
        verify(dbDataSource).insertPost(any())
        verify(dbDataSource).getPost(any())
    }

    @Test
    fun getPostDetailNoInternetError() {
        given(apiDataSource.getPost(any())).willReturn(Single.error(NoInternetException()))
        given(dbDataSource.getPost(any())).willReturn(Maybe.just(Post()))
        given(getUser.build()).willReturn(Single.just(User()))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(PostDetail())
        }

        verify(apiDataSource).getPost(any())
        verify(dbDataSource).getPost(any())
    }

    @Test
    fun getPostDetailError() {
        given(apiDataSource.getPost(any())).willReturn(Single.error(ServerException()))
        given(dbDataSource.insertPost(any())).willReturn(Completable.complete())
        given(dbDataSource.getPost(any())).willReturn(Maybe.just(Post()))
        given(getUser.build()).willReturn(Single.just(User()))
        interactor.build().test().also {
            it.assertError(ServerException::class.java)
            it.assertNoValues()
        }
    }

    @Test
    fun getUserError() {
        given(apiDataSource.getPost(any())).willReturn(Single.just(Post()))
        given(dbDataSource.insertPost(any())).willReturn(Completable.complete())
        given(dbDataSource.getPost(any())).willReturn(Maybe.just(Post()))
        given(getUser.build()).willReturn(Single.error(ServerException()))
        interactor.build().test().also {
            it.assertError(ServerException::class.java)
            it.assertNoValues()
        }
    }
}
