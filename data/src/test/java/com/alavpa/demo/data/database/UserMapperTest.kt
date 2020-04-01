package com.alavpa.demo.data.database

import com.alavpa.demo.data.database.model.UserEntity
import com.alavpa.demo.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun userEntityDefaultToUser() {
        val user = UserEntity().toUser()
        assertEquals(User(), user)
    }

    @Test
    fun userDefaultToEntity() {
        val user = User().toEntity()
        assertEquals(UserEntity(), user)
    }

    @Test
    fun userEntityToUser() {
        val user = UserEntity(1, "name", "username", "phone", "email", "website").toUser()
        assertEquals(User(1, "name", "username", "phone", "email", "website"), user)
    }

    @Test
    fun userToEntity() {
        val user = User(1, "name", "username", "phone", "email", "website").toEntity()
        assertEquals(UserEntity(1, "name", "username", "phone", "email", "website"), user)
    }
}
