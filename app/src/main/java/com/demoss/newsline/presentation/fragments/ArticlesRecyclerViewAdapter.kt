package com.demoss.newsline.presentation.fragments

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseRecyclerViewAdapter
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.util.showImage
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticlesRecyclerViewAdapter : BaseRecyclerViewAdapter<Article, ArticlesRecyclerViewAdapter.VH>() {

    var onItemClick: ((Article) -> Unit)? = null

    override val viewHolderFactory: (view: View) -> VH = { view -> VH(view) }
    override val layoutResId: Int = R.layout.item_article
    override var differ: AsyncListDiffer<Article> = AsyncListDiffer(this, DiffUtilItemCallback())

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<Article>(view) {
        override fun bindData(item: Article) {
            view.apply {
                setOnClickListener {
                    onItemClick?.invoke(item)
                }
                GlobalScope.launch {
                    for (i in item.channelProgress) view.pb.progress = i
                }
                pb.progress = 0
                tvTitle.text = item.title
                tvDate.text = item.publishedAt
                tvDescription.text = item.description
                author.text = item.author
                ivPhoto.showImage(item.image)

            }
        }
    }

    inner class DiffUtilItemCallback() : BaseDiffUtilItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
    }
}