package com.ht.RecyclerAdapters.SectionAdapter


enum class Type {
    row,
    header,
    footer
}

data class IndexPath(
    val row: Int,
    val section: Int,
    val type: Type)