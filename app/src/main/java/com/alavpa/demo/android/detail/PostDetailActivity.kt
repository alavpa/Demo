package com.alavpa.demo.android.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alavpa.demo.R
import com.alavpa.demo.android.DemoActivity
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.android.detail.writer.WriterManager
import com.alavpa.demo.presentation.Presenter
import com.alavpa.demo.presentation.detail.PostDetailPresenter
import com.alavpa.demo.presentation.detail.PostDetailViewModel
import kotlinx.android.synthetic.main.activity_detail.pull
import kotlinx.android.synthetic.main.activity_detail.rv_comments
import kotlinx.android.synthetic.main.activity_detail.toolbar
import kotlinx.android.synthetic.main.activity_detail.tv_author
import kotlinx.android.synthetic.main.activity_detail.tv_comments
import kotlinx.android.synthetic.main.activity_detail.tv_description
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity : DemoActivity<PostDetailViewModel>() {

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }

    private val writerManager: WriterManager by inject()
    private val navigation: PostDetailNavigationImpl by inject()
    private val presenter: PostDetailPresenter by viewModel()
    override fun presenter(): Presenter<PostDetailViewModel> = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rv_comments?.layoutManager = LinearLayoutManager(this)
        rv_comments?.adapter = CommentsAdapter(this, writerManager)

        tv_author?.setOnClickListener {
            presenter.onClickAuthor()
        }

        tv_comments?.setOnClickListener {
            presenter.onClickComments()
        }

        pull?.setOnRefreshListener {
            presenter.loadDetails()
        }

        val id = intent.extras?.getLong(EXTRA_ID) ?: 0

        presenter.getRenderLiveData().observe(this, Observer(::render))
        presenter.onCreate(id, navigation)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadDetails()
    }

    private fun render(viewModel: PostDetailViewModel) {
        pull?.isRefreshing = viewModel.showLoading
        supportActionBar?.title = viewModel.title
        tv_description?.text = viewModel.description
        tv_author?.text = viewModel.author

        if (viewModel.showComments) {
            tv_comments?.text = getString(R.string.comments_number, viewModel.commentsNumber)
            rv_comments?.visibility = VISIBLE
            val adapter = rv_comments?.adapter as? CommentsAdapter
            adapter?.addNewItems(viewModel.comments)
        } else {
            tv_comments?.text = getString(R.string.comments)
            rv_comments?.visibility = GONE
        }
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

    override fun navigation(): DemoNavigation {
        return navigation
    }
}
