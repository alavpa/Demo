package com.alavpa.demo.domain.interactors

import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.datasource.DbDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.model.Comment
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetCommentsTest {
    private lateinit var interactor: GetComments
    private val apiDataSource: ApiDataSource = mock()
    private val dbDataSource: DbDataSource = mock()

    @Before
    fun setup() {
        interactor = GetComments(dbDataSource, apiDataSource)
    }

    @Test
    fun getCommentsHappyPath() {
        given(apiDataSource.getComments(any())).willReturn(Single.just(listOf(Comment())))
        given(dbDataSource.insertComments(any())).willReturn(Completable.complete())
        given(dbDataSource.getComments(any())).willReturn(Single.just(listOf(Comment())))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Comment()))
        }

        verify(apiDataSource).getComments(any())
        verify(dbDataSource).insertComments(any())
        verify(dbDataSource).getComments(any())
    }

    @Test
    fun getCommentsNoInternetException() {
        given(apiDataSource.getComments(any())).willReturn(Single.error(NoInternetException()))
        given(dbDataSource.getComments(any())).willReturn(Single.just(listOf(Comment())))
        interactor.build().test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Comment()))
        }

        verify(apiDataSource).getComments(any())
        verify(dbDataSource).getComments(any())
    }

    @Test
    fun getCommentsException() {
        given(apiDataSource.getComments(any())).willReturn(Single.error(IOException()))
        given(dbDataSource.getComments(any())).willReturn(Single.just(listOf(Comment())))
        interactor.build().test().also {
            it.assertError(IOException::class.java)
        }
    }
}
