package com.alavpa.demo.android.main

import android.os.Bundle
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.android.detail.PostDetailActivity
import com.alavpa.demo.android.detail.PostDetailActivity.Companion.EXTRA_ID
import com.alavpa.demo.presentation.main.MainNavigation

class MainNavigationImpl : MainNavigation, DemoNavigation() {

    override fun goToDetail(id: Long) {
        startActivity(PostDetailActivity::class.java, Bundle().apply { putLong(EXTRA_ID, id) })
    }
}
