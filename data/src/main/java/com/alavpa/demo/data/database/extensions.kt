package com.alavpa.demo.data.database

import com.alavpa.demo.data.database.model.CommentEntity
import com.alavpa.demo.data.database.model.PostEntity
import com.alavpa.demo.data.database.model.UserEntity
import com.alavpa.demo.domain.model.Comment
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.domain.model.User

fun PostEntity.toPost(): Post = Post(
    this.id,
    this.userId,
    this.title,
    this.body
)

fun CommentEntity.toComment(): Comment = Comment(
    this.id,
    this.postId,
    this.name,
    this.email,
    this.body
)

fun UserEntity.toUser(): User = User(
    this.id,
    this.name,
    this.username,
    this.phone,
    this.email,
    this.website
)

fun Post.toEntity(): PostEntity = PostEntity(
    this.id,
    this.userId,
    this.title,
    this.body
)

fun User.toEntity(): UserEntity = UserEntity(
    this.id,
    this.name,
    this.username,
    this.phone,
    this.email,
    this.website
)

fun Comment.toEntity(): CommentEntity = CommentEntity(
    this.id,
    this.postId,
    this.title,
    this.email,
    this.body
)
