package com.demoss.newsline.util.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.base.BaseRecyclerViewAdapter

fun RecyclerView.addItemTouchHelperWithCallback(callback: ItemTouchHelper.SimpleCallback) {
    with(ItemTouchHelper(callback)) { attachToRecyclerView(this@addItemTouchHelperWithCallback) }
}

fun <T> RecyclerView.setupSwipeToDelete(
    adapter: BaseRecyclerViewAdapter<T, *>,
    swipeDirection: SwipeDirection,
    onItemDeleteAction: (T) -> Unit
) {
    val swipeAction: (Int) -> Unit = { itemPosition: Int ->
        adapter.apply {
            onItemDeleteAction(differ.currentList[itemPosition])
            val updatedList = differ.currentList.toMutableList()
            updatedList.removeAt(itemPosition)
            differ.submitList(updatedList)
        }
    }

    val actions: Pair<((Int) -> Unit)?, ((Int) -> Unit)?> = when (swipeDirection) {
        SwipeDirection.LEFT -> swipeAction to null
        SwipeDirection.RIGHT -> null to swipeAction
        SwipeDirection.LEFT_AND_RIGHT -> swipeAction to swipeAction
    }

    this.addItemTouchHelperWithCallback(SimpleSwipeItemCallback(actions.first, actions.second))
}

enum class SwipeDirection {
    LEFT,
    RIGHT,
    LEFT_AND_RIGHT
}