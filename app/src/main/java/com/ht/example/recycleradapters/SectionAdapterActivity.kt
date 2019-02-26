package com.ht.example.recycleradapters

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ht.RecyclerAdapters.SectionAdapter.IndexPath
import com.ht.RecyclerAdapters.SectionAdapter.SectionAdapter
import com.ht.RecyclerAdapters.SectionAdapter.SectionType
import com.ht.RecyclerAdapters.SectionAdapter.Type
import kotlinx.android.synthetic.main.activity_section_adapter.*

class SectionAdapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section_adapter)
        val adapter = SectionExampleAdapter(this)
        adapter.sectionType = SectionType.header
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}

class SectionExampleAdapter(val context: Context): SectionAdapter<RecyclerView.ViewHolder>() {

    override fun numberOfSection(): Int {
        return 4
    }

    override fun numberOfRows(section: Int): Int {
        return when (section) {
            0 -> 4
            1 -> 12
            2 -> 19
            else -> 7
        }
    }

    override fun getItemViewType(indexPath: IndexPath): Int {
        return when(indexPath.type) {
            Type.header -> 0
            Type.footer -> 1
            Type.row -> 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, indexPath: IndexPath) {
        (holder as? SectionHeaderHolder)?.apply {
            this.textView.text = "Section #${indexPath.section} Header, OriginPosition: ${getRawPosition(indexPath)}"
        }

        (holder as? SectionFooterHolder)?.apply {
            this.textView.text = "Section #${indexPath.section} Footer, OriginPosition: ${getRawPosition(indexPath)}"
        }

        (holder as? SectionRowHolder)?.apply {
            this.textView.text = "Row: ${indexPath.row}, OriginPosition: ${getRawPosition(indexPath)}"
        }

        when(indexPath.section) {
            0 -> holder.itemView.setBackgroundColor(Color.parseColor("#f5f5f5"))
            1 -> holder.itemView.setBackgroundColor(Color.parseColor("#e0e0e0"))
            2 -> holder.itemView.setBackgroundColor(Color.parseColor("#c0c0c0"))
            3 -> holder.itemView.setBackgroundColor(Color.parseColor("#9f9f9f"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textView = TextView(context)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = lp
        return when(viewType) {
            // Header case
            0 -> {
                SectionHeaderHolder(textView)
            }
            // Footer case
            1 -> {
                SectionFooterHolder(textView)
            }
            // Row case
            else -> {
                SectionRowHolder(textView)
            }
        }
    }
}

class SectionHeaderHolder(val textView: TextView): RecyclerView.ViewHolder(textView) {
    init {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
    }
}
class SectionRowHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
class SectionFooterHolder(val textView: TextView): RecyclerView.ViewHolder(textView) {
    init {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
    }
}