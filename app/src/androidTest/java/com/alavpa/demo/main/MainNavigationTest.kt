package com.alavpa.demo.main

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.alavpa.demo.android.detail.PostDetailActivity
import com.alavpa.demo.android.main.MainActivity
import com.alavpa.demo.android.main.MainNavigationImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainNavigationTest {

    private lateinit var navigation: MainNavigationImpl

    @Rule
    @JvmField
    val rule = IntentsTestRule(MainActivity::class.java, true, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        navigation = MainNavigationImpl()
    }

    @Test
    fun goToDetail() {
        val activity = rule.launchActivity(Intent())
        navigation.attach(activity)
        navigation.goToDetail(5L)
        intended(hasComponent(PostDetailActivity::class.java.name))
        intended(hasExtra(PostDetailActivity.EXTRA_ID, 5L))
        navigation.detach()
    }
}
