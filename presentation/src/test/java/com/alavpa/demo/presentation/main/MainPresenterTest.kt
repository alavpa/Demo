package com.alavpa.demo.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alavpa.demo.domain.interactors.GetPosts
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.presentation.testModule
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import java.io.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.ArgumentCaptor

class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    private val getPosts: GetPosts = mock()
    private val navigation: MainNavigation = mock()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin { modules(listOf(testModule)) }

        presenter = MainPresenter(getPosts)
        presenter.onCreate(navigation)
    }

    @Test
    fun loadPostsHappyPathTest() {

        val mockObserver: Observer<MainViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getPosts.build()).willReturn(Single.just(listOf(Post(1L))))
        presenter.loadPosts()

        val captor = ArgumentCaptor.forClass(MainViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(MainViewModel(showLoading = true), states[0])
        assertEquals(MainViewModel(showLoading = false, posts = listOf(Post(1L))), states[1])
    }

    @Test
    fun loadPostsException() {

        val mockObserver: Observer<MainViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getPosts.build()).willReturn(Single.error(IOException()))
        presenter.loadPosts()

        val captor = ArgumentCaptor.forClass(MainViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(MainViewModel(showLoading = true), states[0])
        assertEquals(MainViewModel(showLoading = false), states[1])
    }

    @Test
    fun onClickPostTest() {
        presenter.onClickPost(Post(4))
        verify(navigation).goToDetail(4)
    }

    @After
    fun close() {
        stopKoin()
    }
}
