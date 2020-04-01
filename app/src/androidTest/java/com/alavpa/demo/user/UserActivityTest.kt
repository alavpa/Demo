package com.alavpa.demo.user

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.alavpa.demo.R
import com.alavpa.demo.android.user.UserActivity
import com.alavpa.demo.presentation.user.UserPresenter
import com.alavpa.demo.presentation.user.UserViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class UserActivityTest {

    private val presenter: UserPresenter = mock()
    private val liveData = MutableLiveData<UserViewModel>()

    private val testModule = module {
        viewModel(override = true) { presenter }
    }

    @Rule
    @JvmField
    val rule = ActivityTestRule(UserActivity::class.java, true, false)

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
        verify(presenter).onCreate(any(), any())
        verify(presenter).loadUser()
    }

    @Test
    fun showLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(showLoading = true)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        Assert.assertTrue(pull.isRefreshing)
    }

    @Test
    fun hideLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(showLoading = false)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        Assert.assertFalse(pull.isRefreshing)
    }

    @Test
    fun loadUser() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(title = "name", name = "username", phone = "phone", email = "email", website = "website")
        }

        Espresso.onView(allOf(CoreMatchers.instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText("name")))
        Espresso.onView(withId(R.id.tv_name)).check(matches(withText("username")))
        Espresso.onView(withId(R.id.tv_phone)).check(matches(withText("phone")))
        Espresso.onView(withId(R.id.tv_email)).check(matches(withText("email")))
        Espresso.onView(withId(R.id.tv_website)).check(matches(withText("website")))
    }

    @Test
    fun onClickPhone() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(title = "name", name = "username", phone = "phone", email = "email", website = "website")
        }

        Espresso.onView(withId(R.id.tv_phone)).perform(click())
        verify(presenter).onClickPhone()
    }

    @Test
    fun onClickEmail() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(title = "name", name = "username", phone = "phone", email = "email", website = "website")
        }

        Espresso.onView(withId(R.id.tv_email)).perform(click())
        verify(presenter).onClickEmail()
    }

    @Test
    fun onClickWeb() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = UserViewModel(title = "name", name = "username", phone = "phone", email = "email", website = "website")
        }

        Espresso.onView(withId(R.id.tv_website)).perform(click())
        verify(presenter).onClickWeb()
    }
}
