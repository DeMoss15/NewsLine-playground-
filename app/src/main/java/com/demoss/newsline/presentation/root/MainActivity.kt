package com.demoss.newsline.presentation.root

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseActivity
import com.demoss.newsline.presentation.fragments.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainCommand, MainState, MainViewModel>() {

    override lateinit var viewModel: MainViewModel
    override val layoutResourceId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        supportFragmentManager.beginTransaction()
            .add(container.id, WordsListFragment())
            .commit()
    }

    override fun dispatchState(newStatus: MainState) {
        when (newStatus) {
            is InitialState -> dispatchInitialState()
        }
    }

    private fun dispatchInitialState() {

    }
}