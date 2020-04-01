package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.UserResponse
import com.alavpa.demo.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun userResponseDefaultToUser() {
        val user = UserResponse().toUser()
        assertEquals(User(), user)
    }

    @Test
    fun userResponseToUser() {
        val user = UserResponse(1, "name", "username", "phone", "email", "website").toUser()
        assertEquals(User(1, "name", "username", "phone", "email", "website"), user)
    }
}
