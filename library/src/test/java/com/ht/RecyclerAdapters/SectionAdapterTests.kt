package com.ht.RecyclerAdapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.ht.RecyclerAdapters.SectionAdapter.IndexPath
import com.ht.RecyclerAdapters.SectionAdapter.SectionAdapter
import com.ht.RecyclerAdapters.SectionAdapter.SectionType
import com.ht.RecyclerAdapters.SectionAdapter.Type
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import androidx.test.runner.AndroidJUnit4

class TestHolder(view: View): RecyclerView.ViewHolder(view)

@RunWith(AndroidJUnit4::class)
class SectionAdapterTests {
    var context = ApplicationProvider.getApplicationContext<Context>()

    val adapter = TestSectionAdapter(context)
    @Test fun headerTypeIndexPath() {
        adapter.sectionType = SectionType.header


        val header0 = adapter.getIndexPath(0)
        assertEquals(header0, IndexPath(-1, 0, Type.header))

        val header1 = adapter.getIndexPath(4)
        assertEquals(header1, IndexPath(-1, 1, Type.header))

        val header2 = adapter.getIndexPath(13)
        assertEquals(header2, IndexPath(-1, 2, Type.header))

        val row0_0 = adapter.getIndexPath(1)
        assertEquals(IndexPath(0,0, Type.row), row0_0)

        val row0_2 = adapter.getIndexPath(3)
        assertEquals(IndexPath(2,0,Type.row),row0_2)

        val row1_0 = adapter.getIndexPath(5)
        assertEquals(IndexPath(0, 1, Type.row), row1_0)

        val row1_7 = adapter.getIndexPath(12)
        assertEquals(IndexPath(7,1,Type.row), row1_7)

        val row2_0 = adapter.getIndexPath(14)
        assertEquals(IndexPath(0,2, Type.row), row2_0)

        val row2_11 = adapter.getIndexPath(25)
        assertEquals(IndexPath(11, 2, Type.row), row2_11)
    }

    @Test fun noneTypeIndexPath() {
        adapter.sectionType = SectionType.none

        val row0_0 = adapter.getIndexPath(0)
        assertEquals(IndexPath(0,0, Type.row), row0_0)

        val row0_2 = adapter.getIndexPath(2)
        assertEquals(IndexPath(2,0, Type.row), row0_2)


        val row1_0 = adapter.getIndexPath(3)
        assertEquals(IndexPath(0,1, Type.row), row1_0)

        val row1_7 = adapter.getIndexPath(10)
        assertEquals(IndexPath(7, 1, Type.row), row1_7)

        val row2_0 = adapter.getIndexPath(11)
        assertEquals(IndexPath(0,2, Type.row), row2_0)

        val row2_11 = adapter.getIndexPath(22)
        assertEquals(IndexPath(11,2, Type.row), row2_11)
    }

    @Test fun headerAndFooterTypeIndexPath() {
        adapter.sectionType = SectionType.headerAndFooter

        val header0 = adapter.getIndexPath(0)
        assertEquals(IndexPath(-1, 0, Type.header), header0)

        val header1 = adapter.getIndexPath(5)
        assertEquals(IndexPath(-1, 1, Type.header), header1)

        val header2 = adapter.getIndexPath(15)
        assertEquals(IndexPath(-1, 2, Type.header), header2)

        val footer0 = adapter.getIndexPath(4)
        assertEquals(IndexPath(3, 0, Type.footer), footer0)

        val footer1 = adapter.getIndexPath(14)
        assertEquals(IndexPath(8, 1, Type.footer), footer1)

        val footer2 = adapter.getIndexPath(28)
        assertEquals(IndexPath(12, 2, Type.footer), footer2)

        val row0_0 = adapter.getIndexPath(1)
        assertEquals(IndexPath(0,0, Type.row), row0_0)

        val row0_2 = adapter.getIndexPath(3)
        assertEquals(IndexPath(2,0,Type.row),row0_2)

        val row1_0 = adapter.getIndexPath(6)
        assertEquals(IndexPath(0, 1, Type.row), row1_0)

        val row1_7 = adapter.getIndexPath(13)
        assertEquals(IndexPath(7,1,Type.row), row1_7)

        val row2_0 = adapter.getIndexPath(16)
        assertEquals(IndexPath(0,2, Type.row), row2_0)

        val row2_11 = adapter.getIndexPath(27)
        assertEquals(IndexPath(11, 2, Type.row), row2_11)
    }

    @Test fun itemCount() {
        adapter.sectionType = SectionType.header
        assertEquals(26, adapter.itemCount)

        adapter.sectionType = SectionType.headerAndFooter
        assertEquals(29, adapter.itemCount)

        adapter.sectionType = SectionType.none
        assertEquals(23, adapter.itemCount)
    }

    inner class TestSectionAdapter(val context: Context):SectionAdapter<TestHolder>() {
        override fun getItemViewType(indexPath: IndexPath): Int {
            return 1
        }


        override fun numberOfRows(section: Int): Int {
            when(section) {
                0 -> return 3
                1 -> return 8
                else -> return 12
            }
        }

        override fun numberOfSection(): Int {
            return 3
        }


        override fun onBindViewHolder(holder: TestHolder, indexPath: IndexPath) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
            val tv = TextView(context)
            return TestHolder(tv)
        }

    }
}
