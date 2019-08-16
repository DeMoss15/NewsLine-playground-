package com.demoss.newsline.presentation.spizjeno

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

/**
 * @author Konstantin Tskhovrebov (aka terrakok) on 18.06.17.
 */
class ProgressAdapterDelegate : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is ProgressItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_progress))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {}

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}