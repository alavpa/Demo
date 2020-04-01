package com.alavpa.demo.detail

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.alavpa.demo.CustomMatchers.withItemCount
import com.alavpa.demo.R
import com.alavpa.demo.android.detail.PostDetailActivity
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.writers.EmailWriter
import com.alavpa.demo.presentation.detail.PostDetailPresenter
import com.alavpa.demo.presentation.detail.PostDetailViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
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
class PostDetailActivityTest {

    private val presenter: PostDetailPresenter = mock()
    private val liveData = MutableLiveData<PostDetailViewModel>()

    private val testModule = module {
        viewModel(override = true) { presenter }
    }

    @Rule
    @JvmField
    val rule = ActivityTestRule(PostDetailActivity::class.java, true, false)

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
        verify(presenter).loadDetails()
    }

    @Test
    fun showLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = PostDetailViewModel(showLoading = true)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        assertTrue(pull.isRefreshing)
    }

    @Test
    fun hideLoading() {
        val activity = rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = PostDetailViewModel(showLoading = false)
        }

        val pull = activity.findViewById<SwipeRefreshLayout>(R.id.pull)
        assertFalse(pull.isRefreshing)
    }

    @Test
    fun loadPosts() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = PostDetailViewModel(
                title = "title",
                description = "description",
                author = "author",
                showComments = false,
                commentsNumber = 5,
                comments = listOf(Comment(1, 2, "comment1", "email", "content")))
        }

        onView(withId(R.id.tv_description)).check(matches(withText("description")))
        onView(withId(R.id.tv_author)).check(matches(withText("author")))
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar)))).check(matches(withText("title")))
        onView(withId(R.id.tv_comments)).check(matches(withText(R.string.comments)))
        onView(withId(R.id.rv_comments)).check(matches(not(isDisplayed())))
    }

    @Test
    fun loadPostsComments() {
        rule.launchActivity(null)

        rule.runOnUiThread {
            liveData.value = PostDetailViewModel(
                title = "title",
                description = "description",
                author = "author",
                showComments = true,
                commentsNumber = 5,
                comments = listOf(Comment(1, 2, "comment1", "email", "content")))
        }

        onView(withId(R.id.tv_description)).check(matches(withText("description")))
        onView(withId(R.id.tv_author)).check(matches(withText("author")))
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar)))).check(matches(withText("title")))

        onView(withId(R.id.tv_comments)).check(matches(withText(rule.activity.getString(R.string.comments_number, 5))))
        onView(withId(R.id.rv_comments)).check(matches(isDisplayed()))

        onView(withText("comment1")).check(matches(isDisplayed()))
        onView(withText("content")).check(matches(isDisplayed()))
        onView(withText("email")).check(matches(isDisplayed()))

        onView(withId(R.id.rv_comments)).check(matches(withItemCount(1)))
    }

    @Test
    fun performClickComments() {
        val activity = rule.launchActivity(null)
        activity.runOnUiThread {
            liveData.value = PostDetailViewModel(
                title = "title",
                description = "description",
                author = "author",
                showComments = true,
                commentsNumber = 5,
                comments = listOf(Comment(1, 2, "comment1", "email", "content")))
        }

        onView(withId(R.id.tv_comments)).perform(click())

        verify(presenter).onClickComments()
    }

    @Test
    fun performClickAuthor() {
        val activity = rule.launchActivity(null)
        val writer: EmailWriter = mock()
        given(writer.write()).willReturn("email")
        activity.runOnUiThread {
            liveData.value = PostDetailViewModel(
                title = "title",
                description = "description",
                author = "author",
                showComments = true,
                commentsNumber = 5,
                comments = listOf(Comment(1, 2, "comment1", "email", "content")))
        }

        onView(withId(R.id.tv_author)).perform(click())

        verify(presenter).onClickAuthor()
    }
}
