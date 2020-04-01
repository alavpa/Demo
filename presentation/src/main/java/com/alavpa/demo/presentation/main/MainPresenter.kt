package com.alavpa.demo.presentation.main

import com.alavpa.demo.domain.interactors.GetPosts
import com.alavpa.demo.domain.model.Post
import com.alavpa.demo.presentation.Presenter

class MainPresenter(private val getPosts: GetPosts) : Presenter<MainViewModel>() {

    private lateinit var navigation: MainNavigation
    fun onCreate(navigation: MainNavigation) {
        this.navigation = navigation
    }

    fun loadPosts(page: Int = 0) {
        render(viewModel.copy(showLoading = true))
        getPosts.page = viewModel.page
        getPosts.exec(
            {
                render(viewModel.copy(showLoading = false))
            },
            {
                render(viewModel.copy(posts = viewModel.posts.toMutableList().apply { addAll(it) }, showLoading = false, page = page))
            }
        )
    }

    fun onClickPost(post: Post) {
        navigation.goToDetail(post.id)
    }

    fun nextPage(last: Int) {
        if (last == viewModel.posts.size - 1 && !viewModel.showLoading) {
            loadPosts(viewModel.page + 1)
        }
    }

    override fun default(): MainViewModel = MainViewModel()
}
