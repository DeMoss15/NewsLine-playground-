package com.demoss.newsline.presentation.root

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseActivity
import com.demoss.newsline.presentation.fragments.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainAction, MainState, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel<MainViewModel>()
    override val layoutResourceId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun dispatchState(newStatus: MainState) {
        when (newStatus) {
            is InitialState -> showFragment(NewsFragment.TAG) { NewsFragment() }
        }
    }

    private fun showFragment(tag: String, fabric: () -> Fragment) {
        with(supportFragmentManager.findFragmentByTag(tag)) {
            if (this != null) executeTransaction { attach(this@with) }
            else executeTransaction { add(container.id, fabric(), tag) }
        }
    }

    private fun executeTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
        supportFragmentManager.beginTransaction().transaction().commit()
    }
}
