package com.ht.RecyclerAdapters.SectionAdapter

import androidx.recyclerview.widget.RecyclerView

enum class SectionType {
    headerAndFooter,
    header,
    none;

    internal val extraViewCnt: Int
    get() {
        return when(this) {
            headerAndFooter -> 2
            header -> 1
            none -> 0
        }
    }
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

    fun getIndexPath(originPosition: Int): IndexPath {
        var position = 0
        var section = 0

        when(sectionType) {
            SectionType.none -> return getNoneTypeIndexPath(originPosition)
        }

        for (i in 0 until (numberOfSection())) {
            val sectionRowCount = numberOfRows(i)

            val padding: Int = when(sectionType) {
                SectionType.headerAndFooter -> i * 2 + 1
                SectionType.header -> i
                SectionType.none -> 0
            }

            if (originPosition > (position + sectionRowCount + padding)) {
                position += sectionRowCount
                section += 1
                continue
            } else {
                val offset: Int = when(sectionType) {
                    SectionType.headerAndFooter -> i * 2 + 1
                    SectionType.header -> i + 1
                    SectionType.none -> 0
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

    fun getRawPosition(indexPath: IndexPath): Int {
        var rawPostion = 0

        for(i in 0 until indexPath.section) {
            rawPostion += numberOfRows(i)
        }

        return when(indexPath.type) {
            Type.header -> rawPostion + (sectionType.extraViewCnt * indexPath.section)
            Type.footer -> rawPostion + (sectionType.extraViewCnt * indexPath.section) + numberOfRows(indexPath.section) + 1
            Type.row -> rawPostion + indexPath.row + (sectionType.extraViewCnt * indexPath.section + 1)
        }
    }

    override fun getItemCount(): Int {
        val sectionCount = numberOfSection()
        var itemCount = 0

        for (i in 0 until sectionCount) {
            itemCount += numberOfRows(i)
        }

        return itemCount + (sectionCount * sectionType.extraViewCnt)
    }


    private fun getNoneTypeIndexPath(originPosition: Int): IndexPath {
        var position = 0
        var section = 0

        for (i in 0 until (numberOfSection())) {
            val sectionRowCount = numberOfRows(i)

            if (originPosition >= (position + sectionRowCount)) {
                position += sectionRowCount
                section += 1
                continue
            } else {

                position = originPosition - position
                section = i
                break
            }
        }

        return IndexPath(position, section, Type.row)
    }

}