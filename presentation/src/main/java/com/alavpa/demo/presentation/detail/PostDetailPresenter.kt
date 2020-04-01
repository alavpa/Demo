package com.alavpa.demo.presentation.detail

import com.alavpa.demo.domain.interactors.GetComments
import com.alavpa.demo.domain.interactors.GetPostDetail
import com.alavpa.demo.presentation.Presenter
import timber.log.Timber

class PostDetailPresenter(
    private val getPostDetail: GetPostDetail,
    private val getComments: GetComments
) : Presenter<PostDetailViewModel>() {

    private var postId: Long = 0
    private lateinit var postDetailNavigation: PostDetailNavigation
    fun onCreate(id: Long, postDetailNavigation: PostDetailNavigation) {
        this.postId = id
        this.postDetailNavigation = postDetailNavigation
    }

    fun loadDetails() {
        render(viewModel.copy(showLoading = true, showComments = false))
        getPostDetail.id = postId
        getPostDetail.exec(
            {
                Timber.e(it)
                render(viewModel.copy(showLoading = false))
                postDetailNavigation.showError {
                    postDetailNavigation.finish()
                }
            },
            {
                render(
                    viewModel.copy(
                        showLoading = false,
                        title = it.title,
                        authorId = it.user.id,
                        author = it.user.name,
                        description = it.description
                    )
                )
            }
        )
    }

    fun finish() {
        postDetailNavigation.finish()
    }

    fun onClickComments() {
        if (viewModel.showComments) {
            render(viewModel.copy(showComments = false))
        } else {
            loadComments()
        }
    }

    private fun loadComments() {
        render(viewModel.copy(showLoading = true))
        getComments.id = postId
        getComments.exec(
            {
                Timber.e(it)
                render(viewModel.copy(showLoading = false))
            },
            {
                render(
                    viewModel.copy(
                        showLoading = false,
                        showComments = true,
                        commentsNumber = it.size,
                        comments = it
                    )
                )
            }
        )
    }

    fun onClickAuthor() {
        postDetailNavigation.goToUser(viewModel.authorId)
    }

    override fun default(): PostDetailViewModel = PostDetailViewModel()
}
