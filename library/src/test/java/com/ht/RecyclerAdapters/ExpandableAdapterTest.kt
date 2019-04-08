package com.ht.RecyclerAdapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.ht.RecyclerAdapters.ExpandableAdapter.ExpandableAdapter
import com.ht.RecyclerAdapters.ExpandableAdapter.ExpandableItem
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class ExpandableAdapterTest {
    val context = ApplicationProvider.getApplicationContext<Context>()


    @Test
    fun `removeItem should remove only child and change expanded state`() {
        val adapter = MockAdapter()
        val items = getItems()
        adapter.setItems(items)

        val willRemoveItem = adapter.getItem(0) as ExpandableItem
        adapter.addItems(adapter.mItems[0], willRemoveItem.children)
        assertEquals(20, adapter.mItems.size)
        assertEquals(true, adapter.expanded(adapter.mItems[0]))

        adapter.removeItems(adapter.mItems[0], willRemoveItem.children)
        assertEquals(10, adapter.mItems.size)
        assertEquals(false, adapter.expanded(adapter.mItems[0]))
    }

    @Test
    fun `addItem should add only child and change expanded state`() {
        val adapter = MockAdapter()
        val items = getItems()
        adapter.setItems(items)

        val willAddItem = adapter.getItem(0) as ExpandableItem
        adapter.addItems(adapter.mItems[0], willAddItem.children)
        assertEquals(true, adapter.expanded(adapter.mItems[0]))
        assertEquals(20, adapter.mItems.size)
        adapter.addItems(adapter.mItems[0], willAddItem.children)
    }

    fun getItems(): List<ItemList> {
        val zeroDepthItems = (0..9).map { ItemList(it) }
        zeroDepthItems.forEach {
            it.children = (0..9).map { ItemList(it)  }
            it.children.forEach { firstDepth ->
                firstDepth.children = (0..9).map { ItemList(it) }
                firstDepth.children.forEach {
                    it.children = (0..9).map { ItemList(it) }
                }
            }
        }
        return zeroDepthItems
    }

    inner class MockAdapter: ExpandableAdapter<ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, position: Int, depth: Int) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(context)
            return ViewHolder(textView)
        }

    }
}



class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
class ItemList(
    var number: Int) : ExpandableItem {
    override var children: List<ItemList> = ArrayList()

}