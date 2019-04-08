package com.ht.example.recycleradapters

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.AlwaysOnHotwordDetector
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ht.RecyclerAdapters.ExpandableAdapter.ExpandableAdapter
import com.ht.RecyclerAdapters.ExpandableAdapter.ExpandableItem
import kotlinx.android.synthetic.main.activity_expandable.*

class ExpandableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable)
        val adapter = Adapter()
        adapter.setItems(getItems())
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
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

    inner class Adapter: ExpandableAdapter<ExpandableHolder>() {

        override fun onBindViewHolder(holder: ExpandableHolder, position: Int, depth: Int) {
            val item = getItem(position) as ItemList
            val text: String

            when(depth) {
                0 -> {
                    text = "${depth} - ${item.number}"
                    holder.itemView.setBackgroundColor(Color.parseColor("#fafafa"))
                }
                1 -> {
                    text = "\t" + "${depth} - ${item.number}"
                    holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"))
                }
                2 -> {
                    text = "\t\t" + "${depth} - ${item.number}"
                    holder.itemView.setBackgroundColor(Color.parseColor("#DDDDDD"))
                }
                3 -> {
                    text = "\t\t\t" + "${depth} - ${item.number}"
                    holder.itemView.setBackgroundColor(Color.parseColor("#CCCCCC"))
                }
                else -> {
                    text ="depth not found"
                    holder.itemView.setBackgroundColor(Color.parseColor("#aaaaaa"))
                }
            }
            holder.textView.text = text
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableHolder {
            val textView = TextView(this@ExpandableActivity)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60)
            textView.layoutParams = lp

            return ExpandableHolder(textView)
        }

    }
}

class ExpandableHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
class ItemList(
    var number: Int) : ExpandableItem {
    override var children: List<ItemList> = ArrayList()

}

