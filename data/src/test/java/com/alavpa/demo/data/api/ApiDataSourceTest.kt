package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.CommentResponse
import com.alavpa.demo.data.api.model.PostResponse
import com.alavpa.demo.data.api.model.UserResponse
import com.alavpa.demo.domain.datasource.ApiDataSource
import com.alavpa.demo.domain.exceptions.NoInternetException
import com.alavpa.demo.domain.exceptions.ServerException
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import java.io.IOException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ApiDataSourceTest {

    private lateinit var dataSource: ApiDataSource
    private val apiClient: ApiClient = mock()

    @Before
    fun setup() {
        dataSource = ApiDataSourceImpl(apiClient)
    }

    @Test
    fun getPostsHappyPathTest() {
        given(apiClient.posts()).willReturn(Single.just(Response.success(listOf(PostResponse(1)))))
        dataSource.getPosts().test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Post(1)))
        }
    }

    @Test
    fun getPostsNoInternetErrorClientTest() {
        given(apiClient.posts()).willReturn(Single.error(IOException()))
        dataSource.getPosts().test().also {
            it.assertError(NoInternetException::class.java)
        }
    }

    @Test
    fun getPostsServerErrorClientTest() {
        given(apiClient.posts()).willReturn(Single.just(getResponseError()))
        dataSource.getPosts().test().also {
            it.assertError(ServerException::class.java)
        }
    }

    @Test
    fun getPostHappyPathTest() {
        given(apiClient.post(any())).willReturn(Single.just(Response.success(PostResponse(1))))
        dataSource.getPost(5).test().also {
            it.assertNoErrors()
            it.assertValue(Post(1))
        }
    }

    @Test
    fun getPostNoInternetErrorClientTest() {
        given(apiClient.post(any())).willReturn(Single.error(IOException()))
        dataSource.getPost(5).test().also {
            it.assertError(NoInternetException::class.java)
        }
    }

    @Test
    fun getPostServerErrorClientTest() {
        given(apiClient.post(any())).willReturn(Single.just(getResponseError()))
        dataSource.getPost(5).test().also {
            it.assertError(ServerException::class.java)
        }
    }

    @Test
    fun getUserHappyPathTest() {
        given(apiClient.user(any())).willReturn(Single.just(Response.success(UserResponse(1))))
        dataSource.getUser(5).test().also {
            it.assertNoErrors()
            it.assertValue(User(1))
        }
    }

    @Test
    fun getUserNoInternetErrorClientTest() {
        given(apiClient.user(any())).willReturn(Single.error(IOException()))
        dataSource.getUser(5).test().also {
            it.assertError(NoInternetException::class.java)
        }
    }

    @Test
    fun getUserServerErrorClientTest() {
        given(apiClient.user(any())).willReturn(Single.just(getResponseError()))
        dataSource.getUser(5).test().also {
            it.assertError(ServerException::class.java)
        }
    }

    @Test
    fun getCommentsHappyPathTest() {
        given(apiClient.comments(any())).willReturn(Single.just(Response.success(listOf(CommentResponse(1)))))
        dataSource.getComments(5).test().also {
            it.assertNoErrors()
            it.assertValue(listOf(Comment(1)))
        }
    }

    @Test
    fun getCommentsNoInternetErrorClientTest() {
        given(apiClient.comments(any())).willReturn(Single.error(IOException()))
        dataSource.getComments(5).test().also {
            it.assertError(NoInternetException::class.java)
        }
    }

    @Test
    fun getCommentsServerErrorClientTest() {
        given(apiClient.comments(any())).willReturn(Single.just(getResponseError()))
        dataSource.getComments(5).test().also {
            it.assertError(ServerException::class.java)
        }
    }

    private fun <T> getResponseError(): Response<T> {
        return Response.error(404, ResponseBody.create(MediaType.parse("plain/text"), ""))
    }
}
