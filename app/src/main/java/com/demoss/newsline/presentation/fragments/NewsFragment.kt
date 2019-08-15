package com.demoss.newsline.presentation.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseFragment
import com.demoss.newsline.base.BaseRecyclerViewAdapter
import com.demoss.newsline.base.mvvm.PAGINATOR_LOAD_NEXT_PAGE
import com.demoss.newsline.base.mvvm.PAGINATOR_REFRESH
import com.demoss.newsline.base.mvvm.PaginatorAction
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.presentation.spizheno.inflate
import com.demoss.newsline.util.pagination.PaginatorViewState
import com.demoss.newsline.util.showImage
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.fragment_news_data.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<PaginatorAction, PaginatorViewState<Article>, NewsViewModel>() {

    companion object {
        const val TAG = "com.demoss.newsline.presentation.fragments.NewsFragment"
    }

    override val layoutResourceId: Int = R.layout.fragment_news_data
    override val viewModel by viewModel<NewsViewModel>()

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        paginalView.init(
            { viewModel.executeAction(PAGINATOR_REFRESH) },
            { viewModel.executeAction(PAGINATOR_LOAD_NEXT_PAGE) },
            { old, new -> old == new},
            object: AdapterDelegate<MutableList<Any>>() {
                override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = VH(parent.inflate(R.layout.item_article))

                override fun isForViewType(items: MutableList<Any>, position: Int): Boolean = items[position] is Article

                override fun onBindViewHolder(
                    items: MutableList<Any>,
                    position: Int,
                    holder: RecyclerView.ViewHolder,
                    payloads: MutableList<Any>
                ) {
                    if (holder is VH && items[position] is Article)
                        holder.bindData(items[position] as Article)
                }
            }
        )
    }

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<Article>(view) {
        override fun bindData(item: Article) {
            view.apply {
                tvTitle.text = item.tile
                tvDate.text = item.publishedAt
                tvDescription.text = item.description
                author.text = item.author
                ivPhoto.showImage(item.image)
            }
        }
    }

    override fun dispatchState(newStatus: PaginatorViewState<Article>) {
//        if (newStatus is DATA) rv.adapter = ArticlesRecyclerViewAdapter(newStatus.data.toMutableList())
        paginalView.render(newStatus)
    }
}