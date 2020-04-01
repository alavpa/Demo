package com.alavpa.demo.android.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alavpa.demo.R
import com.alavpa.demo.android.DemoActivity
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.presentation.Presenter
import com.alavpa.demo.presentation.main.MainPresenter
import com.alavpa.demo.presentation.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.pull
import kotlinx.android.synthetic.main.activity_main.rv_posts
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DemoActivity<MainViewModel>() {
    private val presenter: MainPresenter by viewModel()
    private val navigation: MainNavigationImpl by inject()

    override fun presenter(): Presenter<MainViewModel> {
        return presenter
    }

    override fun navigation(): DemoNavigation {
        return navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        rv_posts?.layoutManager = LinearLayoutManager(this)
        rv_posts?.adapter = PostAdapter(this, onClickPost = presenter::onClickPost)

        rv_posts?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayout = recyclerView.layoutManager as? LinearLayoutManager
                val last = linearLayout?.findLastVisibleItemPosition() ?: 0
                presenter.nextPage(last)
            }
        })

        pull?.setOnRefreshListener {
            presenter.loadPosts()
        }

        presenter.getRenderLiveData().observe(this, Observer(::render))
        presenter.onCreate(navigation)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadPosts()
    }

    private fun render(viewModel: MainViewModel) {
        pull?.isRefreshing = viewModel.showLoading

        val adapter = rv_posts?.adapter as? PostAdapter
        adapter?.addNewItems(viewModel.posts)
    }
}
