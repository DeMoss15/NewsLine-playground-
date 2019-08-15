package com.demoss.newsline.presentation.spizheno

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.util.pagination.*
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.synthetic.main.view_paginal_render.view.*

class PaginalRenderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var refreshCallback: (() -> Unit)? = null
    private var nextPageCallback: (() -> Unit)? = null
    private var itemDiff: ((old: Any, new: Any) -> Boolean)? = null

    private var adapter: PaginalAdapter? = null

    init {
        inflate(R.layout.view_paginal_render, true)
        swipeToRefresh.setOnRefreshListener { refreshCallback?.invoke() }
        emptyView.setRefreshListener { refreshCallback?.invoke() }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    fun init(
        refreshCallback: () -> Unit,
        nextPageCallback: () -> Unit,
        itemDiff: (old: Any, new: Any) -> Boolean,
        vararg delegate: AdapterDelegate<MutableList<Any>>
    ) {
        this.refreshCallback = refreshCallback
        this.nextPageCallback = nextPageCallback
        this.itemDiff = itemDiff
        adapter = PaginalAdapter(*delegate)
        recyclerView.adapter = adapter
    }

    fun render(state: PaginatorViewState<Article>) {
        post {
            when (state) {
                is EMPTY -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = true
                    adapter?.update(emptyList(), false)
                    emptyView.showEmptyData()
                    swipeToRefresh.visible(true)
                }
                is EMPTY_PROGRESS -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(true)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
                    emptyView.hide()
                    swipeToRefresh.visible(false)
                }
                is EMPTY_ERROR -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
//                    emptyView.showEmptyError(state.error.userMessage(resourceManager))
                    swipeToRefresh.visible(true)
                }
                is DATA -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is REFRESH -> {
                    swipeToRefresh.isRefreshing = true
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is PAGE_PROGRESS -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, true)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is RELEASED -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = true
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
            }
        }
    }

    private inner class PaginalAdapter(vararg delegate: AdapterDelegate<MutableList<Any>>) :
        AsyncListDifferDelegationAdapter<Any>(
            object : DiffUtil.ItemCallback<Any>() {
                override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                    if (oldItem === newItem) return true
                    return itemDiff?.invoke(oldItem, newItem) ?: false
                }

                override fun getChangePayload(oldItem: Any, newItem: Any) = Any() //disable default blink animation

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
            }
        ) {
        var fullData = false

        init {
            items = mutableListOf()
            delegatesManager.addDelegate(ProgressAdapterDelegate())
            delegate.forEach { delegatesManager.addDelegate(it) }
        }

        fun update(data: List<Any>, isPageProgress: Boolean) {
            items = mutableListOf<Any>().apply {
                addAll(data)
                if (isPageProgress) add(ProgressItem)
            }
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: MutableList<Any?>
        ) {
            super.onBindViewHolder(holder, position, payloads)
            if (!fullData && position >= items.size - 10) nextPageCallback?.invoke()
        }
    }
}