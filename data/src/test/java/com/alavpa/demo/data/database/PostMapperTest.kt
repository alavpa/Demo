package com.alavpa.demo.data.database

import com.alavpa.demo.data.database.model.PostEntity
import com.alavpa.demo.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperTest {

    @Test
    fun postEntityDefaultToPost() {
        val post = PostEntity().toPost()
        assertEquals(Post(), post)
    }

    @Test
    fun postDefaultToEntity() {
        val post = Post().toEntity()
        assertEquals(PostEntity(), post)
    }

    @Test
    fun postEntityToPost() {
        val post = PostEntity(1, 2, "title", "body").toPost()
        assertEquals(Post(1, 2, "title", "body"), post)
    }

    @Test
    fun postToEntity() {
        val post = Post(1, 2, "title", "body").toEntity()
        assertEquals(PostEntity(1, 2, "title", "body"), post)
    }
}
