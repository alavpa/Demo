package com.alavpa.demo.data.api

import com.alavpa.demo.data.api.model.CommentResponse
import com.alavpa.demo.data.api.model.PostResponse
import com.alavpa.demo.data.api.model.UserResponse
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User

fun PostResponse.toPost(): Post = Post(
    this.id ?: 0,
    this.userId ?: 0,
    this.title ?: "",
    this.body ?: ""
)

fun UserResponse.toUser(): User = User(
    this.id ?: 0,
    this.name ?: "",
    this.username ?: "",
    this.phone ?: "",
    this.email ?: "",
    this.website ?: ""
)

fun CommentResponse.toComment(): Comment = Comment(
    this.id ?: 0,
    this.postId ?: 0,
    this.name ?: "",
    this.email ?: "",
    this.body ?: ""
)
