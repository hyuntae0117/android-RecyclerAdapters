package com.ht.RecyclerAdapters.SectionAdapter

import androidx.recyclerview.widget.RecyclerView

abstract class SectionAdapter<VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    open fun numberOfSection(): Int {
        return 1
    }

    abstract fun numberOfRows(section: Int): Int

    abstract fun getItemViewType(indexPath: IndexPath): Int

    abstract fun onBindViewHolder(holder: VH, indexPath: IndexPath)

    private fun getIndexPath(originPosition: Int): IndexPath {
        var position = 0
        var section = 0
        for (i in 0 until (numberOfSection())) {
            val sectionRowCount = numberOfRows(i)

            if (originPosition > (position + sectionRowCount + i * 2 + 1)) {
                position += sectionRowCount
                section += 1
                continue
            } else {
                position = originPosition - ((i * 2) + 1 + position)
                section = i
                break
            }
        }
        val type: Type = when(position) {
            -1 -> Type.header
            numberOfRows(section) -> Type.footer
            else -> Type.row

        }
        return IndexPath(position, section, type)
    }

    override fun getItemCount(): Int {
        val sectionCount = numberOfSection()
        var itemCount = 0

        for (i in 0 until sectionCount) {
            itemCount += numberOfRows(i)
        }
        return itemCount + (sectionCount * 2)
    }


    final override fun onBindViewHolder(holder: VH, position: Int) {
        val indexPath = getIndexPath(position)
        onBindViewHolder(holder, indexPath)
    }

    final override fun getItemViewType(position: Int): Int {
        val indexPath = getIndexPath(position)
        return getItemViewType(indexPath)
    }

}