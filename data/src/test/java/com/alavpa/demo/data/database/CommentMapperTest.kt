package com.alavpa.demo.data.database

import com.alavpa.demo.data.database.model.CommentEntity
import com.alavpa.demo.domain.model.Comment
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperTest {

    @Test
    fun commentEntityDefaultToComment() {
        val comment = CommentEntity().toComment()
        assertEquals(Comment(), comment)
    }

    @Test
    fun commentDefaultToEntity() {
        val comment = Comment().toEntity()
        assertEquals(CommentEntity(), comment)
    }

    @Test
    fun commentEntityToComment() {
        val comment = CommentEntity(1, 2, "name", "email", "body").toComment()
        assertEquals(Comment(1, 2, "name", "email", "body"), comment)
    }

    @Test
    fun commentToEntity() {
        val comment = Comment(1, 2, "name", "email", "body").toEntity()
        assertEquals(CommentEntity(1, 2, "name", "email", "body"), comment)
    }
}
