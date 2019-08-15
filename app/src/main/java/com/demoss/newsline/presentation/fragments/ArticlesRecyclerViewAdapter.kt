package com.demoss.newsline.presentation.fragments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseRecyclerViewAdapter
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.presentation.spizheno.inflate
import com.demoss.newsline.util.showImage
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlinx.android.synthetic.main.item_article.view.*

class ArticlesRecyclerViewAdapter(val data: MutableList<Article>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val adapterDelegateManager: AdapterDelegatesManager<List<Article>> = AdapterDelegatesManager<List<Article>>().apply {
        addDelegate(getDelegate())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapterDelegateManager.onCreateViewHolder(parent, viewType)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapterDelegateManager.onBindViewHolder(data, position, holder)

    private fun getDelegate() = object: AdapterDelegate<List<Article>>() {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = VH(parent.inflate(R.layout.item_article))

        override fun isForViewType(items: List<Article>, position: Int): Boolean = true

        override fun onBindViewHolder(
            items: List<Article>,
            position: Int,
            holder: RecyclerView.ViewHolder,
            payloads: MutableList<Any>
        ) {
            if (holder is VH) holder.bindData(items[position])
        }
    }

//    private fun getDelegate() = adapterDelegate<Article, Article>(R.layout.item_article, { item, items, position -> item is Article }) {
//        this.itemView.apply {
//            tvTitle.text = item.tile
//            tvDate.text = item.publishedAt
//            tvDescription.text = item.description
//            author.text = item.author
//            ivPhoto.showImage(item.image)
//        }
//    }

    /*override val viewHolderFactory: (view: View) -> VH = { view -> VH(view) }
    override val layoutResId: Int = R.layout.item_article
    override var differ: AsyncListDiffer<Article> = AsyncListDiffer(this, DiffUtilItemCallback())*/

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

   /* inner class DiffUtilItemCallback(): BaseDiffUtilItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
    }*/
}