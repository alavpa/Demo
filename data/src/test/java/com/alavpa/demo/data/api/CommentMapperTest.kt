package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.CommentResponse
import com.alavpa.demo.domain.model.Comment
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperTest {

    @Test
    fun commentResponseDefaultToComment() {
        val user = CommentResponse().toComment()
        assertEquals(Comment(), user)
    }

    @Test
    fun commentResponseToComment() {
        val comment = CommentResponse(1, 2, "name", "email", "body").toComment()
        assertEquals(Comment(1, 2, "name", "email", "body"), comment)
    }
}
