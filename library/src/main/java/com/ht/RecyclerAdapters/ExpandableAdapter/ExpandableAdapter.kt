package com.ht.RecyclerAdapters.ExpandableAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList


abstract class ExpandableAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var mItems: MutableList<ExpandableItemSet> = ArrayList()


    fun setItems(items: List<*>) {
        for (item in items) {
            mItems.add(ExpandableItemSet(
                    item!!, false, 0
            ))
        }
    }

    fun getItem(position: Int): Any {
        return mItems[position].item
    }

    final override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (getItem(position) is ExpandableItem) {
            val item = getItem(position) as ExpandableItem
            val _item = mItems[position]
            holder.itemView.setOnClickListener(View.OnClickListener {
                when (expanded(_item)) {
                    true -> removeItems(_item, item.children)
                    false -> addItems(_item, item.children)
                }
            })
            onBindViewHolder(holder, position, _item.depth)
        }
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {}

    override fun getItemViewType(position: Int): Int {
        return mItems[position].depth
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun expanded(item: Any): Boolean? {
        val position = mItems.indexOf(item)
        if(position == -1)
            return null
        return mItems[position].isExpanded
    }

    fun addItems(parent: Any, items: List<*>) {
        val position = mItems.indexOf(parent)
        for (i in items.indices) {
            val item = items[i]
            mItems.add(position + 1 + i, ExpandableItemSet(
                    item!!, false, mItems!![position].depth + 1
            ))
        }
        mItems[position].isExpanded = true
        notifyItemRangeInserted(position + 1, items.size)
        notifyItemChanged(position)
    }

    fun removeItems(parent: Any, items: List<*>) {
        val position = mItems.indexOf(parent)
        for (i in items.indices) {
            if (getItem(position + 1) is ExpandableItem) {
                if (expanded(mItems[position + 1]) == true) {
                    val item = getItem(position + 1) as ExpandableItem
                    removeItems(mItems[position + 1], item.children)
                }
            }
            mItems.removeAt(position + 1)
        }
        mItems[position].isExpanded = false
        notifyItemRangeRemoved(position + 1, items.size)
        notifyItemChanged(position)
    }

    fun clearItems() {
        mItems.clear()
    }

    abstract fun onBindViewHolder(holder: VH, position: Int, depth: Int)
}
