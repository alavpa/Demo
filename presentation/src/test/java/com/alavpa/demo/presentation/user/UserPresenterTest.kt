package com.alavpa.demo.presentation.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alavpa.demo.domain.interactors.GetUser
import com.alavpa.demo.domain.model.User
import com.alavpa.demo.presentation.testModule
import com.nhaarman.mockitokotlin2.any
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

class UserPresenterTest {

    private lateinit var presenter: UserPresenter
    private val getUser: GetUser = mock()
    private val navigation: UserNavigation = mock()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin { modules(listOf(testModule)) }

        presenter = UserPresenter(getUser)
        presenter.onCreate(5L, navigation)
    }

    @Test
    fun loadPostDetailTest() {

        val mockObserver: Observer<UserViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getUser.build()).willReturn(Single.just(User(1L, "name", "username", "phone", "email", "website")))
        presenter.loadUser()

        val captor = ArgumentCaptor.forClass(UserViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(UserViewModel(true), states[0])
        assertEquals(UserViewModel(false, "name", "username", "email", "phone", "website"), states[1])
    }

    @Test
    fun loadPostsException() {

        val mockObserver: Observer<UserViewModel> = mock()
        presenter.getRenderLiveData().observeForever(mockObserver)

        given(getUser.build()).willReturn(Single.error(IOException()))
        presenter.loadUser()

        val captor = ArgumentCaptor.forClass(UserViewModel::class.java)
        verify(mockObserver, times(2)).onChanged(capture(captor))

        val states = captor.allValues
        assertEquals(UserViewModel(true), states[0])
        assertEquals(UserViewModel(false), states[1])
    }

    @Test
    fun finish() {
        presenter.finish()
        verify(navigation).finish()
    }

    @Test
    fun onClickWeb() {
        presenter.onClickWeb()
        verify(navigation).goToWeb(any())
    }

    @Test
    fun onClickEmail() {
        presenter.onClickEmail()
        verify(navigation).goToEmail(any())
    }

    @Test
    fun onClickPhone() {
        presenter.onClickPhone()
        verify(navigation).goToPhone(any())
    }

    @After
    fun close() {
        stopKoin()
    }
}
