package com.ht.RecyclerAdapters.SectionAdapter

import androidx.recyclerview.widget.RecyclerView

enum class SectionType {
    headerAndFooter,
    header
}

abstract class SectionAdapter<VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    var sectionType: SectionType = SectionType.headerAndFooter

    open fun numberOfSection(): Int {
        return 1
    }

    abstract fun numberOfRows(section: Int): Int

    abstract fun getItemViewType(indexPath: IndexPath): Int

    abstract fun onBindViewHolder(holder: VH, indexPath: IndexPath)

    final override fun onBindViewHolder(holder: VH, position: Int) {
        val indexPath = getIndexPath(position)
        onBindViewHolder(holder, indexPath)
    }

    final override fun getItemViewType(position: Int): Int {
        val indexPath = getIndexPath(position)
        return getItemViewType(indexPath)
    }

    private fun getIndexPath(originPosition: Int): IndexPath {
        var position = 0
        var section = 0

        for (i in 0 until (numberOfSection())) {
            val sectionRowCount = numberOfRows(i)

            val padding: Int = when(sectionType) {
                SectionType.headerAndFooter -> i * 2 + 1
                SectionType.header -> i
            }
            if (originPosition > (position + sectionRowCount + padding)) {
                position += sectionRowCount
                section += 1
                continue
            } else {
                val offset: Int = when(sectionType) {
                    SectionType.headerAndFooter -> i * 2 + 1
                    SectionType.header -> i + 1
                }

                position = originPosition - (offset + position)
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

        val sectionExtraViewCnt = when(sectionType) {
            SectionType.header -> 1
            SectionType.headerAndFooter -> 2
        }
        return itemCount + (sectionCount * sectionExtraViewCnt)
    }



}