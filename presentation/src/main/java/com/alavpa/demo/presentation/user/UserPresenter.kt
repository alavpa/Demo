package com.alavpa.demo.presentation.user

import com.alavpa.demo.domain.interactors.GetUser
import com.alavpa.demo.presentation.Presenter
import timber.log.Timber

class UserPresenter(private val getUser: GetUser) : Presenter<UserViewModel>() {

    private lateinit var userNavigation: UserNavigation
    private var id: Long = 0

    fun onCreate(id: Long, userNavigation: UserNavigation) {
        this.userNavigation = userNavigation
        this.id = id
    }

    fun loadUser() {
        render(viewModel.copy(showLoading = true))
        getUser.id = id
        getUser.exec(
            {
                render(viewModel.copy(showLoading = false))
                Timber.e(it)
            },
            {
                render(
                    viewModel.copy(
                        showLoading = false,
                        title = it.name,
                        name = it.username,
                        phone = it.phone,
                        email = it.email,
                        website = it.website
                    )
                )
            }
        )
    }

    fun finish() {
        userNavigation.finish()
    }

    fun onClickWeb() {
        userNavigation.goToWeb(viewModel.website)
    }

    fun onClickEmail() {
        userNavigation.goToEmail(viewModel.email)
    }

    fun onClickPhone() {
        userNavigation.goToPhone(viewModel.phone)
    }

    override fun default(): UserViewModel = UserViewModel()
}
