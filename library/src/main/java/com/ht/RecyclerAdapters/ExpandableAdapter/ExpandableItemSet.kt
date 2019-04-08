package com.ht.RecyclerAdapters.ExpandableAdapter

class ExpandableItemSet(item: Any, expanded: Boolean, depth: Int) {
    var depth: Int = 0
        internal set
    var isExpanded: Boolean = false
        internal set
    var item: Any
        internal set

    init {
        this.item = item
        this.isExpanded = expanded
        this.depth = depth
    }

}
