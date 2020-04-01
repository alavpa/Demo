package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.PostResponse
import com.alavpa.demo.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperTest {

    @Test
    fun postResponseDefaultToPost() {
        val post = PostResponse().toPost()
        assertEquals(Post(), post)
    }

    @Test
    fun postResponseToPost() {
        val post = PostResponse(1, 2, "title", "body").toPost()
        assertEquals(Post(1, 2, "title", "body"), post)
    }
}
