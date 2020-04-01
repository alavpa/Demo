package com.alavpa.demo.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.alavpa.demo.R
import com.alavpa.demo.android.main.MainActivity
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.presentation.main.MainPresenter
import com.alavpa.demo.presentation.main.MainViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val presenter: MainPresenter = mock()
    private val liveData = MutableLiveData<MainViewModel>()

    private val testModule = module {
        viewModel(override = true) { presenter }
    }

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules(testModule)
        given(presenter.getRenderLiveData()).willReturn(liveData)
    }

    @Test
    fun onStartTest() {
        rule.launchActivity(null)
        verify(presenter).onCreate(any())
        verify(presenter).loadPosts()
    }

    @Test
    fun showLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = MainViewModel(showLoading = true)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        assertTrue(pull.isRefreshing)
    }

    @Test
    fun hideLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = MainViewModel(showLoading = false)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        assertFalse(pull.isRefreshing)
    }

    @Test
    fun loadPosts() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = MainViewModel(posts = listOf(Post(title = "test")))
        }

        onView(withText("test")).check(matches(isDisplayed()))
    }

    @Test
    fun onClickPost() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = MainViewModel(posts = listOf(Post(id = 1, title = "test")))
        }

        onView(withText("test")).perform(click())

        verify(presenter).onClickPost(Post(id = 1, title = "test"))
    }
}
