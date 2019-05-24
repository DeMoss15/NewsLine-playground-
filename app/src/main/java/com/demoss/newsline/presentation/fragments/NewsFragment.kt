package com.demoss.newsline.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseFragment
import com.demoss.newsline.domain.model.Article
import kotlinx.android.synthetic.main.fragment_news_data.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<NewsAction, NewsState, NewsViewModel>() {

    companion object {
        const val TAG = "com.demoss.newsline.presentation.fragments.NewsFragment"
    }

    override val layoutResourceId: Int = R.layout.fragment_news_empty_progress
    override val viewModel by viewModel<NewsViewModel>()

    private val rvAdapter: ArticlesRecyclerViewAdapter by lazy { ArticlesRecyclerViewAdapter(viewModel.viewModelScope) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_news_constraint.loadLayoutDescription(R.xml.fragment_news)

        rvAdapter.onItemClick = { viewModel.loadItem(it) }
        viewModel.executeAction(LoadNews)

        rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    override fun dispatchState(newStatus: NewsState) {
        when (newStatus) {
            is NewsData -> {
                setConstraintState(showDataState(newStatus.data))
            }
        }
    }

    private fun showDataState(data: List<Article>): Int = R.id.data.also { rvAdapter.dispatchData(data) }

    private fun setMessageState(@StringRes message: Int): Int = R.id.message.also { tvMessage.text = getString(message) }

    private fun setConstraintState(@IdRes id: Int) {
        fragment_news_constraint.setState(id, 0, 0)
    }
}