package com.alavpa.demo.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alavpa.demo.domain.exceptions.ServerException
import com.alavpa.demo.domain.interactors.GetComments
import com.alavpa.demo.domain.interactors.GetPostDetail
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.PostDetail
import com.alavpa.demo.domain.model.User
import com.alavpa.demo.presentation.testModule
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.ArgumentCaptor
import java.io.IOException

class DetailPresenterTest {

    private lateinit var presenter: PostDetailPresenter
    private val getPostDetail: GetPostDetail = mock()
    private val getComments: GetComments = mock()
    private val navigation: PostDetailNavigation = mock()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin { modules(listOf(testModule)) }

        presenter = PostDetailPresenter(getPostDetail, getComments)
        presenter.onCreate(5L, navigation)
    }

    @Test
    fun loadPostDetailTest() {

        val mockObserver: Observer<PostDetailViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getPostDetail.build()).willReturn(Single.just(PostDetail(1L, "title", "description", User(3, "author"))))
        presenter.loadDetails()

        val captor = ArgumentCaptor.forClass(PostDetailViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(PostDetailViewModel(true), states[0])
        assertEquals(PostDetailViewModel(false, "title", "description", 3, "author", 0), states[1])
    }

    @Test
    fun loadPostsException() {

        val mockObserver: Observer<PostDetailViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getPostDetail.build()).willReturn(Single.error(IOException()))
        presenter.loadDetails()

        val captor = ArgumentCaptor.forClass(PostDetailViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(PostDetailViewModel(true), states[0])
        assertEquals(PostDetailViewModel(false), states[1])
    }

    @Test
    fun onClickAuthorTest() {
        presenter.render(PostDetailViewModel(false, "title", "description", 3, "author", 0))

        presenter.onClickAuthor()

        verify(navigation).goToUser(3)
    }

    @Test
    fun onClickCommentsShowCommentsTest() {

        val initViewModel = PostDetailViewModel(false, "title", "description", 3, "author", 0)
        presenter.render(initViewModel)

        val mockObserver: Observer<PostDetailViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getComments.build()).willReturn(Single.just(listOf(Comment(1, 2, "title", "desc", "content"))))
        presenter.onClickComments()

        val captor = ArgumentCaptor.forClass(PostDetailViewModel::class.java)
        verify(mockObserver, times(3)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(initViewModel, states[0])
        assertEquals(initViewModel.copy(true), states[1])
        assertEquals(initViewModel.copy(false, showComments = true, commentsNumber = 1, comments = listOf(Comment(1, 2, "title", "desc", "content"))), states[2])
    }

    @Test
    fun onClickCommentsHideCommentsTest() {
        val initViewModel = PostDetailViewModel(false, "title", "description", 3, "author", 0, showComments = true)
        presenter.render(initViewModel)

        val mockObserver: Observer<PostDetailViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        presenter.onClickComments()

        val captor = ArgumentCaptor.forClass(PostDetailViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(initViewModel, states[0])
        assertEquals(initViewModel.copy(showComments = false), states[1])
    }

    @Test
    fun onClickCommentsGetCommentsErrorTest() {

        val initViewModel = PostDetailViewModel(false, "title", "description", 3, "author", 0)
        presenter.render(initViewModel)

        val mockObserver: Observer<PostDetailViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getComments.build()).willReturn(Single.error(ServerException()))
        presenter.onClickComments()

        val captor = ArgumentCaptor.forClass(PostDetailViewModel::class.java)
        verify(mockObserver, times(3)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(initViewModel, states[0])
        assertEquals(initViewModel.copy(true), states[1])
        assertEquals(initViewModel.copy(false), states[2])
    }

    @After
    fun close() {
        stopKoin()
    }
}
