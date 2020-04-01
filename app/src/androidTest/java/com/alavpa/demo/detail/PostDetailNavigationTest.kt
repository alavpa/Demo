package com.alavpa.demo.detail

import android.content.DialogInterface
import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.alavpa.demo.R
import com.alavpa.demo.android.detail.PostDetailActivity
import com.alavpa.demo.android.detail.PostDetailNavigationImpl
import com.alavpa.demo.android.user.UserActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailNavigationTest {

    private lateinit var navigation: PostDetailNavigationImpl

    @Rule
    @JvmField
    val ruleIntent = IntentsTestRule(PostDetailActivity::class.java, true, false)

    @Rule
    @JvmField
    val ruleActivity = ActivityTestRule(PostDetailActivity::class.java, true, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        navigation = PostDetailNavigationImpl()
    }

    @Test
    fun goToUser() {
        val activity = ruleIntent.launchActivity(Intent())
        navigation.attach(activity)
        navigation.goToUser(5L)
        intended(hasComponent(UserActivity::class.java.name))
        intended(hasExtra(PostDetailActivity.EXTRA_ID, 5L))
        navigation.detach()
    }

    @Test
    fun showError() {
        val activity = ruleIntent.launchActivity(Intent())
        navigation.attach(activity)

        ruleIntent.runOnUiThread {
            navigation.showAlertDialog(
                R.string.detail_message_error,
                android.R.string.ok,
                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
            )
        }

        onView(withText(R.string.detail_message_error)).check(matches(isDisplayed()))
        navigation.detach()
    }

    @Test
    fun finish() {
        val activity = ruleIntent.launchActivity(null)
        navigation.attach(activity)
        navigation.finish()
        assertTrue(activity.isFinishing)
        navigation.detach()
    }
}
