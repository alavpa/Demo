package com.alavpa.demo.user

import android.content.Intent
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.alavpa.demo.android.user.UserActivity
import com.alavpa.demo.android.user.UserNavigationImpl
import com.alavpa.demo.presentation.user.UserPresenter
import com.alavpa.demo.presentation.user.UserViewModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class UserNavigationTest {

    private val presenter: UserPresenter = mock()
    private lateinit var navigation: UserNavigationImpl
    private val liveData = MutableLiveData<UserViewModel>()

    private val testModule = module {
        viewModel(override = true) { presenter }
    }

    @Rule
    @JvmField
    val rule = IntentsTestRule(UserActivity::class.java, true, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules(testModule)
        given(presenter.getRenderLiveData()).willReturn(liveData)
        navigation = UserNavigationImpl()
    }

    @Test
    fun goToEmail() {
        val activity = rule.launchActivity(null)
        navigation.attach(activity)
        navigation.goToEmail("email")
        intended(hasAction(Intent.ACTION_SEND))
        intended(hasType("plain/text"))
        intended(hasExtra(Intent.EXTRA_EMAIL, arrayOf("email")))
        navigation.detach()
        rule.finishActivity()
    }

    @Test
    fun goToWeb() {
        val activity = rule.launchActivity(null)
        navigation.attach(activity)
        navigation.goToWeb("url.com")
        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(Uri.parse("http://url.com")))
        navigation.detach()
        rule.finishActivity()
    }

    @Test
    fun goToPhone() {
        val activity = rule.launchActivity(null)
        navigation.attach(activity)
        navigation.goToPhone("445566")
        intended(hasAction(Intent.ACTION_DIAL))
        intended(hasData(Uri.parse("tel:445566")))
        navigation.detach()
        rule.finishActivity()
    }

    @Test
    fun finish() {
        val activity = rule.launchActivity(null)
        navigation.attach(activity)
        navigation.finish()
        assertTrue(activity.isFinishing)
        navigation.detach()
        rule.finishActivity()
    }
}
