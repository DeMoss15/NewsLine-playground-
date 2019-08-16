package com.demoss.newsline.presentation.spizjeno

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

inline fun <reified T> mutableAdapterDelegate(
    @LayoutRes layout: Int,
    noinline onBindViewHolder: (VH<T>.()->Unit)? = null,
    noinline bind: View.(item: T) -> Unit
) = object : AdapterDelegate<MutableList<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        VH<T>(parent.inflate(layout), bind)

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean = items[position] is T

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        if (items[position] is T) {
            @Suppress("UNCHECKED_CAST")
            holder as VH<T>
            onBindViewHolder?.invoke(holder)
            holder.bindData(items[position] as T)
        }
    }
}

class VH<T>(val view: View, val bind: View.(item: T) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bindData(item: T) = view.bind(item)
}