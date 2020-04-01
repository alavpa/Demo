package com.alavpa.demo.android.user

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.alavpa.demo.R
import com.alavpa.demo.android.DemoActivity
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.presentation.Presenter
import com.alavpa.demo.presentation.user.UserPresenter
import com.alavpa.demo.presentation.user.UserViewModel
import kotlinx.android.synthetic.main.activity_user.pull
import kotlinx.android.synthetic.main.activity_user.toolbar
import kotlinx.android.synthetic.main.activity_user.tv_email
import kotlinx.android.synthetic.main.activity_user.tv_name
import kotlinx.android.synthetic.main.activity_user.tv_phone
import kotlinx.android.synthetic.main.activity_user.tv_website
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : DemoActivity<UserViewModel>() {

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }

    private val presenter: UserPresenter by viewModel()
    override fun presenter(): Presenter<UserViewModel> = presenter

    private val navigation: UserNavigationImpl by inject()

    override fun navigation(): DemoNavigation {
        return navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pull?.setOnRefreshListener {
            presenter.loadUser()
        }

        tv_website?.setOnClickListener {
            presenter.onClickWeb()
        }

        tv_email?.setOnClickListener {
            presenter.onClickEmail()
        }

        tv_phone?.setOnClickListener {
            presenter.onClickPhone()
        }

        val id = intent.getLongExtra(EXTRA_ID, 0)

        presenter.getRenderLiveData().observe(this, Observer(::render))
        presenter.onCreate(id, navigation)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun render(viewModel: UserViewModel) {
        supportActionBar?.title = viewModel.title
        tv_name?.text = viewModel.name
        tv_email?.text = viewModel.email
        tv_phone?.text = viewModel.phone
        tv_website?.text = viewModel.website

        pull?.isRefreshing = viewModel.showLoading
    }

    override fun onResume() {
        super.onResume()
        presenter.loadUser()
    }
}
